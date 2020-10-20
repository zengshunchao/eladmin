package com.study.web.service.impl;

import com.study.web.dao.*;
import com.study.web.dto.BackGroundOrderInfoDto;
import com.study.web.dto.BackGroundOrderQueryDto;
import com.study.web.dto.CourseInfoDto;
import com.study.web.dto.ExportOrderInfoDto;
import com.study.web.entity.Order;
import com.study.web.entity.OrderCourseRel;
import com.study.web.entity.Picture;
import com.study.web.entity.WxUser;
import com.study.web.service.BackGroundOrderService;
import com.study.web.util.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author zsc
 * @date 2020/10/15 0015 22:06
 */
@Service
public class BackGroundOrderServiceImpl implements BackGroundOrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderCourseRelDao orderCourseRelDao;
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private WxUserDao wxUserDao;
    @Autowired
    private PictureDao pictureDao;

    @Override
    public List<BackGroundOrderInfoDto> queryOrderByLimit(BackGroundOrderQueryDto orderQueryDto, Pageable pageable) {
        if (!StringUtils.isEmpty(orderQueryDto.getStartTime())) {
            try {
                // 时间段查询
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String[] timeStr = orderQueryDto.getStartTime().split(",");
                Date startTime = format.parse(timeStr[0]);
                Date endTime = format.parse(timeStr[1]);
                orderQueryDto.setCreateTime(startTime);
                orderQueryDto.setEndTime(endTime);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        int startNum = pageable.getPageNumber() > 0 ? pageable.getPageNumber() * pageable.getPageSize() : 0;
        // 分页查询订单
        List<BackGroundOrderInfoDto> backGroundOrderInfoDtos = orderDao.queryOrderLimit(orderQueryDto, startNum, pageable.getPageSize());
        backGroundOrderInfoDtos.stream().forEach(info -> {
            queryCourseInfo(info);
        });
        return backGroundOrderInfoDtos;
    }

    @Override
    public int totalOrder(BackGroundOrderQueryDto orderQueryDto) {
        if (!StringUtils.isEmpty(orderQueryDto.getStartTime())) {
            try {
                // 时间段查询
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String[] timeStr = orderQueryDto.getStartTime().split(",");
                Date startTime = format.parse(timeStr[0]);
                Date endTime = format.parse(timeStr[1]);
                orderQueryDto.setCreateTime(startTime);
                orderQueryDto.setEndTime(endTime);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return orderDao.totalOrder(orderQueryDto);
    }

    @Override
    public BackGroundOrderInfoDto queryOrderById(Long id) {
        BackGroundOrderInfoDto backGroundOrderInfoDto = orderDao.queryOrderInfoById(id);
        queryCourseInfo(backGroundOrderInfoDto);
        return backGroundOrderInfoDto;
    }

    @Override
    public void updateCheckTimeAndStatus(Order order) {
        order.setStatus(Constants.FINISHED);
        order.setFinishTime(new Date());
        orderDao.update(order);
    }

    @Override
    public List<ExportOrderInfoDto> exportOrderExcel() {

        // 查询所有订单
        List<BackGroundOrderInfoDto> backGroundOrderInfoDtos = orderDao.queryAllOrder();
        // 导出所有订单
        List<ExportOrderInfoDto> excelList = new ArrayList<>();
        if (backGroundOrderInfoDtos.size() != 0) {
            Iterator iterator = backGroundOrderInfoDtos.iterator();
            while (iterator.hasNext()) {
                BackGroundOrderInfoDto orderInfo = (BackGroundOrderInfoDto) iterator.next();
                queryCourseInfo(orderInfo);
                ExportOrderInfoDto exportOrderInfoDto = new ExportOrderInfoDto();
                String status = orderStatus(orderInfo.getStatus());
                exportOrderInfoDto.setStatus(status);
                BeanUtils.copyProperties(orderInfo, exportOrderInfoDto);
                excelList.add(exportOrderInfoDto);
            }
        }
        return excelList;
    }

    private void queryCourseInfo(BackGroundOrderInfoDto backGroundOrderInfoDto) {
        if (backGroundOrderInfoDto != null) {
            OrderCourseRel orderCourseRel = new OrderCourseRel();
            orderCourseRel.setOrderId(backGroundOrderInfoDto.getId());
            List<OrderCourseRel> orderCourseRels = orderCourseRelDao.queryAll(orderCourseRel);
            if (orderCourseRels != null && orderCourseRels.size() != 0) {
                // 拼接名称
                List<String> courseNameList = new ArrayList<>();
                List<String> shareNameList = new ArrayList<>();
                List<String> coverPathList = new ArrayList<>();
                int num = 0;
                for (OrderCourseRel rel : orderCourseRels) {
                    CourseInfoDto courseInfoDto = courseDao.queryById(rel.getCourseId());
                    courseNameList.add(courseInfoDto.getCourseName());
                    if (rel.getShareId() != null) {
                        WxUser user = wxUserDao.queryById(rel.getShareId());
                        shareNameList.add(user.getNickName());
                    }
                    num = num + rel.getCourseNumber();

                    // 查询课程封面
                    List<Picture> covers = pictureDao.queryPictureListByType(rel.getCourseId(), Constants.PICTURE_TYPE_COVER);
                    if (covers.size() != 0) {
                        coverPathList.add(covers.get(0).getPictureUrl());
                    }
                }
                // 拼接课程名称
                String name = StringUtils.join(courseNameList.toArray(), ",");
                backGroundOrderInfoDto.setCourseName(name);
                backGroundOrderInfoDto.setCourseNumber(num);
                if (shareNameList.size() != 0) {
                    String shareName = StringUtils.join(shareNameList.toArray(), ",");
                    backGroundOrderInfoDto.setShareName(shareName);
                } else {
                    backGroundOrderInfoDto.setShareName("-");
                }
                if (coverPathList.size() != 0) {
                    backGroundOrderInfoDto.setCoursePicturePath(coverPathList.get(0));
                }
            }
        }
    }

    private String orderStatus(Integer status) {
        if (status == Constants.UNPAID) {
            return "待支付";
        } else if (status == Constants.UNUSED) {
            return "待使用";
        } else if (status == Constants.FINISHED) {
            return "已完成";
        } else if (status == Constants.CANCELED) {
            return "已取消";
        } else {
            return "-";
        }
    }
}
