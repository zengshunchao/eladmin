package com.study.web.service;

import com.study.web.dto.CourseInfoDto;
import com.study.web.dto.CourseQueryDto;
import com.study.web.dto.WxUserDto;
import com.study.web.entity.WxUser;

import java.util.List;

public interface WxUserService {

    /**
     * 查询所有数据
     *
     * @param wxUserDto
     * @return
     */
    List<WxUserDto> queryAll(WxUserDto wxUserDto);

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    WxUser queryById(Long id);
}
