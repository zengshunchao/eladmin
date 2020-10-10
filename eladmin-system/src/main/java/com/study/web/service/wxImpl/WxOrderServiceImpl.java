package com.study.web.service.wxImpl;

import com.study.web.dao.*;
import com.study.web.dto.CourseInfoDto;
import com.study.web.dto.OrderCourseRelDto;
import com.study.web.dto.OrderDto;
import com.study.web.entity.Order;
import com.study.web.entity.OrderCourseRel;
import com.study.web.entity.Picture;
import com.study.web.entity.WxUser;
import com.study.web.service.WxOrderService;
import com.study.web.util.Constants;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
     * 新增数据
     *
     * @param order 实例对象
     * @return 实例对象
     */
    @Override
    public Order insert(Order order) {
        orderDao.insert(order);
        return order;
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
        return orderDto;
    }
}