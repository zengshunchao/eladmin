package com.study.web.service.wxImpl;

import com.study.utils.CalculateUtil;
import com.study.utils.OrderCodeUtil;
import com.study.web.dao.*;
import com.study.web.dto.CourseInfoDto;
import com.study.web.dto.OrderCourseRelDto;
import com.study.web.dto.OrderDto;
import com.study.web.dto.OrderInfoDto;
import com.study.web.entity.Order;
import com.study.web.entity.OrderCourseRel;
import com.study.web.entity.Picture;
import com.study.web.entity.WxUser;
import com.study.web.service.WxOrderService;
import com.study.web.util.Constants;
import com.study.web.wxPaySdk.WXPayUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
            // 查询订单购买人姓名和手机号
            WxUser buyUser = wxUserDao.queryById(orderDto.getWxUserId());
            orderDto.setRealName(buyUser.getRealName());
            orderDto.setPhone(buyUser.getPhone());
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
}