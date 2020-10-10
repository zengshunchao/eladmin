package com.study.web.service.impl;

import com.study.web.dao.WxUserDao;
import com.study.web.dto.WxUserDto;
import com.study.web.entity.WxUser;
import com.study.web.service.WxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WxUserServiceImpl implements WxUserService {

    @Autowired
    private WxUserDao wxUserDao;

    @Override
    public List<WxUserDto> queryAll(WxUserDto wxUserDto) {
        return wxUserDao.queryWxUserList(wxUserDto);
    }

    @Override
    public WxUser queryById(Long id) {
        return wxUserDao.queryById(id);
    }
}
