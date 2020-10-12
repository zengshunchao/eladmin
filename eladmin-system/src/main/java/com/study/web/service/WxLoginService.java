package com.study.web.service;

import com.study.web.dto.WxUserDto;
import com.study.web.entity.WxUser;

public interface WxLoginService {

    /**
     * 微信小程序授权登录
     *
     * @param wxUserDto
     */
    public WxUser wxLogin(WxUserDto wxUserDto);

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    public WxUser queryById(Long id);

    /**
     * 更新用户信息
     * @param wxUser
     * @return
     */
    WxUser update(WxUser wxUser);
}
