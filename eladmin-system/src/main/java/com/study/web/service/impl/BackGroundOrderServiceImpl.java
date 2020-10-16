package com.study.web.service.impl;

import com.study.web.dao.*;
import com.study.web.dto.BackGroundOrderInfoDto;
import com.study.web.dto.BackGroundOrderQueryDto;
import com.study.web.dto.CourseInfoDto;
import com.study.web.entity.OrderCourseRel;
import com.study.web.entity.Picture;
import com.study.web.entity.WxUser;
import com.study.web.service.BackGroundOrderService;
import com.study.web.util.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        // 分页查询订单
        List<BackGroundOrderInfoDto> backGroundOrderInfoDtos = orderDao.queryOrderLimit(orderQueryDto, pageable.getPageNumber(), pageable.getPageSize());
        backGroundOrderInfoDtos.stream().forEach(info -> {
            OrderCourseRel orderCourseRel = new OrderCourseRel();
            orderCourseRel.setOrderId(info.getId());
            List<OrderCourseRel> orderCourseRels = orderCourseRelDao.queryAll(orderCourseRel);
            if (orderCourseRels != null && orderCourseRels.size() != 0) {
                // 拼接名称
                List<String> courseNameList = new ArrayList<>();
                List<String> shareNameList = new ArrayList<>();
                int num = 0;
                for (OrderCourseRel rel : orderCourseRels) {
                    CourseInfoDto courseInfoDto = courseDao.queryById(rel.getCourseId());
                    courseNameList.add(courseInfoDto.getCourseName());
                    if (rel.getShareId() != null) {
                        WxUser user = wxUserDao.queryById(rel.getShareId());
                        shareNameList.add(user.getNickName());
                    }
                    num = num + rel.getCourseNumber();
                }
                // 拼接课程名称
                String name = StringUtils.join(courseNameList.toArray(), ",");
                info.setCourseName(name);
                info.setCourseNumber(num);
                if (shareNameList.size() != 0) {
                    String shareName = StringUtils.join(shareNameList.toArray(), ",");
                    info.setShareName(shareName);
                }
            }
        });
        return backGroundOrderInfoDtos;
    }

    @Override
    public int totalOrder(BackGroundOrderQueryDto orderQueryDto) {
        return orderDao.totalOrder(orderQueryDto);
    }

    @Override
    public BackGroundOrderInfoDto queryOrderById(Long id) {
        BackGroundOrderInfoDto backGroundOrderInfoDto = orderDao.queryOrderInfoById(id);
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
                }
                if (coverPathList.size() != 0) {
                    backGroundOrderInfoDto.setCoursePicturePath(coverPathList.get(0));
                }
            }
        }

        return backGroundOrderInfoDto;
    }
}
