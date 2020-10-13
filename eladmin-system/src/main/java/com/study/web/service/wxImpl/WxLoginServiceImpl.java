package com.study.web.service.wxImpl;

import com.alibaba.fastjson.JSONObject;
import com.study.web.dao.WxUserDao;
import com.study.web.dto.WxUserDto;
import com.study.web.entity.Distribution;
import com.study.web.entity.Share;
import com.study.web.entity.WxUser;
import com.study.web.service.WxDistributionService;
import com.study.web.service.WxLoginService;
import com.study.web.service.WxShareService;
import com.study.web.util.Constants;
import com.study.web.util.OkHttpRequestUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class WxLoginServiceImpl implements WxLoginService {

    @Autowired
    private WxUserDao wxUserDao;

    @Autowired
    private WxDistributionService wxDistributionService;

    @Autowired
    private WxShareService wxShareService;

    @Override
    public WxUser wxLogin(WxUserDto wxUserDto) {

        // 查询是否存在该用户
        WxUser wxUser = null;
        if (StringUtils.isNotEmpty(wxUserDto.getOpenId())) {
            wxUser = wxUserDao.queryByOpenId(wxUserDto.getOpenId());
        }
        if (wxUser != null) {
            //是否是分销员
            distribution(wxUser);
            return wxUser;
        }
        // 配置请求参数
        Map<String, String> param = new HashMap<>(4);
        param.put("appid", Constants.WX_APPID);
        param.put("secret", Constants.WX_SECRET);
        param.put("js_code", wxUserDto.getCode());
        param.put("grant_type", Constants.GRANT_TYPE);
        // 请求微信服务器
        String result = OkHttpRequestUtil.sendGet(Constants.WX_LOGIN_URL, param, 5L, 10L);
        if (!StringUtils.isNotEmpty(result)) {
            return null;
        }
        JSONObject json = JSONObject.parseObject(result);
        String session_key = json.getString("session_key");
        String openid = json.getString("openid");
        if (StringUtils.isEmpty(openid)) {
            return null;
        }
        wxUser = wxUserDao.queryByOpenId(openid);
        if (wxUser != null) {
            //是否是分销员
            distribution(wxUser);
            return wxUser;
        }
        if (wxUser == null) {
            WxUser user = new WxUser();
            user.setOpenId(openid);
            user.setSessionKey(session_key);
            user.setNickName(wxUserDto.getNickName());
            user.setGender(wxUserDto.getGender());
            user.setWxLanguage(wxUserDto.getWxLanguage());
            user.setCity(wxUserDto.getCity());
            user.setProvince(wxUserDto.getProvince());
            user.setCountry(wxUserDto.getCountry());
            user.setAvatarUrl(wxUserDto.getAvatarUrl());
            user.setRealName(wxUserDto.getRealName());
            user.setPhone(wxUserDto.getPhone());
            user.setCreateTime(new Date());
            wxUserDao.insert(user);
            return user;
        }
        return wxUser;

    }

    //是否是分销员
    private void distribution(WxUser wxUser){
        if (wxUser != null) {
            Distribution distribution = wxDistributionService.queryByWxUserId(wxUser.getId());
            if(null != distribution){
                wxUser.setDistributionFlag(1);
                wxUser.setParentId(distribution.getParentId());
                wxUser.setShareId(wxUser.getId());
            }else {
                Share share = wxShareService.queryById(wxUser.getId());
                if(null != share){
                    wxUser.setShareId(share.getShareId());
                }
            }

        }
    }

    @Override
    public WxUser queryById(Long id) {
        return wxUserDao.queryById(id);
    }

    @Override
    public WxUser update(WxUser wxUser) {
        this.wxUserDao.update(wxUser);
        return this.queryById(wxUser.getId());
    }
}
