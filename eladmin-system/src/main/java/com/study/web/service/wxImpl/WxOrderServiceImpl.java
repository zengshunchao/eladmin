package com.study.web.service.wxImpl;

import com.study.utils.CalculateUtil;
import com.study.utils.OrderCodeUtil;
import com.study.web.dao.*;
import com.study.web.dto.CourseInfoDto;
import com.study.web.dto.OrderCourseRelDto;
import com.study.web.dto.OrderDto;
import com.study.web.dto.OrderInfoDto;
import com.study.web.entity.*;
import com.study.web.quartz.AutoCheckOrderJob;
import com.study.web.quartz.OrderJob;
import com.study.web.quartz.QuartzManager;
import com.study.web.service.WxOrderService;
import com.study.web.service.WxWalletService;
import com.study.web.service.WxWalletWaterService;
import com.study.web.util.Constants;
import com.study.web.util.TimeUtil;
import com.study.web.wxPaySdk.WXPayUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 订单表(Order)表服务实现类
 *
 * @author zengsc
 * @since 2020-10-09 16:26:20
 */
@Service
public class WxOrderServiceImpl implements WxOrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderCourseRelDao orderCourseRelDao;
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private PictureDao pictureDao;
    @Autowired
    private WxUserDao wxUserDao;
    @Autowired
    private WxPayService wxPayService;
    @Autowired
    private DictDao dictDao;
    @Autowired
    private DistributionDao distributionDao;
    @Autowired
    private CommissionDao commissionDao;
    @Autowired
    private WxWalletService wxWalletService;
    @Autowired
    private WxWalletWaterService wxWalletWaterService;
    @Autowired
    QuartzManager quartzManager;

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Order> queryAllByLimit(int offset, int limit) {
        return orderDao.queryAllByLimit(offset, limit);
    }

    /**
     * 统一下单
     *
     * @param orderInfoDto 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, String> insertOrder(HttpServletRequest request, OrderInfoDto orderInfoDto) throws Exception {

        Map<String, String> map = null;
        // 商户订单号
        String out_trade_no = WXPayUtil.generateNonceStr();
        // 判断是否是新订单
        if (orderInfoDto.getId() != null) {
            OrderDto orderDto = orderDao.queryById(orderInfoDto.getId());
            if (orderDto != null) {
                if (Constants.UNPAID != orderDto.getStatus()) {
                    throw new Exception("未查询到待支付订单");
                }
                Order order = new Order();
                order.setId(orderInfoDto.getId());
                order.setOutTradeNo(out_trade_no);
                orderDao.update(order);

                // 调用统一下单接口
                order.setWxUserId(orderDto.getWxUserId());
                order.setMoney(orderDto.getMoney());
                map = wxPayService.unifiedOrder(request, order);
                // 统一下单成功则将订单商户号一并返回
                map.put("outTradeNo", out_trade_no);
                return map;
            }

        }

        //  获取课程数量，计算金额
        List<OrderCourseRel> courseNums = orderInfoDto.getCourseNums();
        Double money = 0d;
        if (courseNums != null && courseNums.size() != 0) {
            for (OrderCourseRel orderCourseRel : courseNums) {
                // 查询课程信息
                CourseInfoDto course = courseDao.queryById(orderCourseRel.getCourseId());
                // 计算当前课程的总金额
                Double totalMoney = CalculateUtil.mul(course.getCourseMoney(), Double.valueOf(orderCourseRel.getCourseNumber()));
                // 计算所有课程金额总和
                money = CalculateUtil.add(totalMoney, money);
            }
        }
        if (orderInfoDto.getMoney() == null || orderInfoDto.getMoney().compareTo(BigDecimal.valueOf(money)) != 0) {
            throw new Exception("订单金额有误");
        }
        // 添加订单
        Order order = new Order();
        BeanUtils.copyProperties(orderInfoDto, order);
        order.setStatus(Constants.UNPAID);
        order.setOutTradeNo(out_trade_no);
        order.setOrderNumber(OrderCodeUtil.getOrderCodeV2(orderInfoDto.getWxUserId()));
        order.setCreateTime(new Date());
        int resultNum = orderDao.insert(order);

        //添加定时取消任务
        addCancelOrderTask(order);

        //添加订单中课程绑定关系
        for (OrderCourseRel courseRel : courseNums) {
            courseRel.setOrderId(order.getId());
            orderCourseRelDao.insert(courseRel);
        }
        // 本地添加成功就调用微信统一下单接口
        if (resultNum != 0) {
            map = wxPayService.unifiedOrder(request, order);
            if (map != null) {
                map.put("outTradeNo", out_trade_no);
            }
        }
        return map;
    }

    /**
     * 修改数据
     *
     * @param order 实例对象
     * @return 实例对象
     */
    @Override
    public void update(Order order) {
        orderDao.update(order);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return orderDao.deleteById(id) > 0;
    }

    @Override
    public List<OrderDto> queryOrdersByStatus(Order order) {

        // 查询当前微信用户所有订单
        List<OrderDto> orderDtos = orderDao.queryByStatus(order);

        // 订单不为空则查询订单关联的课程
        if (orderDtos != null && orderDtos.size() != 0) {
            for (OrderDto orderDto : orderDtos) {
                courseInfoForOrder(orderDto);
            }
        }
        return orderDtos;
    }

    @Override
    public OrderDto queryOrderByOrderId(Order order) {
        // 订单id查询订单详情
        OrderDto orderDto = orderDao.queryById(order.getId());
        if (orderDto != null) {
            courseInfoForOrder(orderDto);

        }
        return orderDto;
    }

    @Override
    public Order queryOrderByOutTradeNo(String outTradeNo) {
        return orderDao.queryOrderByOutTradeNo(outTradeNo);
    }

    /**
     * 推广订单
     *
     * @param orderDto
     * @return
     */
    @Override
    public List<OrderDto> getShareOrderList(OrderDto orderDto) {
        // 查询当前微信用户推广订单
        List<OrderDto> orderDtos = orderDao.getShareOrderList(orderDto);
        // 订单不为空则查询订单关联的课程
        if (orderDtos != null && orderDtos.size() != 0) {
            for (OrderDto order : orderDtos) {
                courseInfoForOrder(order);
            }
        }
        return orderDtos;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderAndSaveCommission(Order order) {
        // 修改订单状态
        orderDao.update(order);

        // 查询佣金记录,若佣金记录为空则计算佣金
        List<Commission> commissions = commissionDao.queryByOrderId(order.getId());
        if (null == commissions || commissions.size() == 0) {
            // 根据订单id查询关联的课程，获取分享人id
            OrderCourseRel orderCourseRel = new OrderCourseRel();
            orderCourseRel.setOrderId(order.getId());
            List<OrderCourseRel> orderCourseRels = orderCourseRelDao.queryAll(orderCourseRel);
            if (orderCourseRels != null && orderCourseRels.size() != 0) {
                // 因一个订单下的分享人id是相同的，则直接取第一条
                OrderCourseRel courseRel = orderCourseRels.get(0);

                // 判断分享人id是否为空
                if (courseRel.getShareId() != null) {
                    // 根据分享人id查询是否为分销员，不为空则表示为分销员
                    Distribution distribution = distributionDao.queryByWxUserId(courseRel.getShareId());
                    if (distribution != null) {
                        // 计算佣金总和
                        Double sumCommission = orderDao.sumCommission(order.getId());
                        // 计算佣金解锁时间
                        Dict dict = dictDao.queryByDictName(Constants.COMMISSION_DICT_LOCK_TIME_KEY);
                        long currentTimeMillis = System.currentTimeMillis() + Long.valueOf(dict.getDictValue()) * 24 * 3600 * 1000;
                        Date lockTime = TimeUtil.timeStampToDate(currentTimeMillis, "yyyy-MM-dd HH:mm:ss");
                        // 插入佣金表
                        Commission commission = new Commission(order.getId(), BigDecimal.valueOf(sumCommission),
                                courseRel.getShareId(), courseRel.getShareId(), lockTime, Constants.COMMISSION_LOCK_STATUS_YES);
                        commissionDao.insert(commission);
                        // 修改用户钱包金额
                        wxWalletService.updateWalletByCommission(commission);
                        // 插入钱包流水
                        WalletWater water = new WalletWater(commission.getWxUserId(), commission.getCommissionMoney(),
                                Constants.WALLET_WATER_INCOME, "佣金收入");
                        wxWalletWaterService.insert(water);
                        // 上级分销员不为空则计算额外佣金
                        if (distribution.getParentId() != null) {
                            // 查询佣金计算的百分比
                            Dict percentValue = dictDao.queryByDictName(Constants.COMMISSION_DICT_KEY);
                            Double mul = CalculateUtil.mul(sumCommission, Double.valueOf(percentValue.getDictValue()));
                            Commission parentCommission = new Commission(order.getId(), BigDecimal.valueOf(mul), distribution.getParentId(),
                                    courseRel.getShareId(), lockTime, Constants.COMMISSION_LOCK_STATUS_YES);
                            commissionDao.insert(parentCommission);
                            wxWalletService.updateWalletByCommission(parentCommission);
                            WalletWater parentWater = new WalletWater(parentCommission.getWxUserId(), parentCommission.getCommissionMoney(),
                                    Constants.WALLET_WATER_INCOME, "佣金收入");
                            wxWalletWaterService.insert(parentWater);
                        }
                    }
                }
            }
        }
        // 添加定时任务
        addOrderTask(order);
    }

    /**
     * 根据用户和课程查询订单数量
     *
     * @param wxUserId
     * @param courseId
     * @return
     */
    @Override
    public Integer countOrderByCourseAndUser(Long wxUserId, Long courseId) {
        return orderDao.countOrderByCourseAndUser(wxUserId, courseId);
    }

    @Override
    public List<Order> queryAllByQuartz(int status) {
        return orderDao.queryAllByQuartz(status);
    }

    //查询订单课程详细信息
    private void courseInfoForOrder(OrderDto orderDto) {
        List<OrderCourseRelDto> courseList = new ArrayList<>();
        // 根据订单id查询订单关联的课程信息
        OrderCourseRel orderCourseRel = new OrderCourseRel();
        orderCourseRel.setOrderId(orderDto.getId());
        List<OrderCourseRel> orderCourseRels = orderCourseRelDao.queryAll(orderCourseRel);
        // 查询课程详细信息
        if (orderCourseRels != null && orderCourseRels.size() != 0) {
            for (OrderCourseRel rel : orderCourseRels) {
                OrderCourseRelDto orderCourseRelDto = new OrderCourseRelDto();
                CourseInfoDto courseInfoDto = courseDao.queryById(rel.getCourseId());
                WxUser user = wxUserDao.queryById(rel.getShareId());
                orderCourseRelDto.setCourseNumber(rel.getCourseNumber());
                if (user != null) {
                    orderCourseRelDto.setShareName(user.getNickName());
                }
                BeanUtils.copyProperties(courseInfoDto, orderCourseRelDto);
                //查询课程封面图
                List<Picture> covers = pictureDao.queryPictureListByType(rel.getCourseId(), Constants.PICTURE_TYPE_COVER);
                orderCourseRelDto.setCoverPicture(covers);

                // 详情图
                List<Picture> infos = pictureDao.queryPictureListByType(rel.getCourseId(), Constants.PICTURE_TYPE_INFO);
                orderCourseRelDto.setCourseInfoPicture(infos);
                courseList.add(orderCourseRelDto);
            }
            orderDto.setCourseList(courseList);
        }
    }

    /**
     *  添加定时任务
     * @param order
     */
    private void addOrderTask(Order order) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            Dict dict = dictDao.queryByDictName(Constants.AUTO_CHECK_TIME_KEY);
            Map<String, Object> jobMap = new HashMap<>();
            jobMap.put("orderId", order.getId());
            String cron = TimeUtil.transCorn(TimeUtil.afterTime(order.getPayTime(),
                    TimeUtil.DAY, Integer.valueOf(dict.getDictValue())));
            quartzManager.addJob(String.valueOf(order.getId()), "动态订单自动核销任务触发器",
                    String.valueOf(order.getId()), "ORDER_AUTO_CHECK_JOB_GROUP", AutoCheckOrderJob.class, cron, jobMap);
        });
    }

    /**
     * 自动取消订单任务
     * @param order
     */
    private void addCancelOrderTask(Order order){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            Map<String, Object> map = new HashMap<>();
            map.put("orderId", order.getId());
            String cron = TimeUtil.transCorn(TimeUtil.afterTime(order.getCreateTime(), TimeUtil.MINUTE, 1));
            quartzManager.addJob(String.valueOf(order.getId()), "动态订单任务触发器",
                    String.valueOf(order.getId()), "ORDER_JOB_GROUP", OrderJob.class, cron, map);
        });

    }
}