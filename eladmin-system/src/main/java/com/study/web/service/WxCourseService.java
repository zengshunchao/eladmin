package com.study.web.service;

import com.study.web.dto.CourseInfoDto;

import java.util.List;

public interface WxCourseService {

    /**
     * 查询所有当前为上架状态的课程
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    public List<CourseInfoDto> queryList(int pageNo, int pageSize);

    /**
     * 分页查询统计
     *
     * @return
     */
    public int totalList();

    /**
     * 获取课程详情
     *
     * @param id
     * @return
     */
    CourseInfoDto queryCourseById(Long id);
}
