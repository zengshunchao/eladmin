package com.study.web.wxApi;

import com.alibaba.fastjson.JSONObject;
import com.study.web.dto.DistributionDto;
import com.study.web.dto.WxUserDto;
import com.study.web.entity.WxUser;
import com.study.web.service.WxLoginService;
import com.study.web.util.ResponseCode;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("/wxApi/user")
public class WxUserLoginController {

    @Autowired
    private WxLoginService wxLoginService;

    @ApiOperation("微信-授权登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object wxUserLogin(@RequestBody WxUserDto wxUserDto, HttpServletRequest request, HttpServletResponse response) {

        JSONObject jsonObject = new JSONObject();
        try {
            WxUser wxUser = wxLoginService.wxLogin(wxUserDto);
            if (wxUser == null) {
                jsonObject.put("code", ResponseCode.FAIL.getCode());
                jsonObject.put("msg", "获取微信用户信息失败");
                return jsonObject;
            }
            jsonObject.put("code", ResponseCode.SUCCESS.getCode());
            jsonObject.put("msg", ResponseCode.SUCCESS.getMsg());
            jsonObject.put("data", wxUser);
        } catch (Exception e) {
            jsonObject.put("code", ResponseCode.FAIL.getCode());
            jsonObject.put("msg", ResponseCode.FAIL.getMsg());
            log.error("wxUserLogin fail {}", e);
            return jsonObject;
        }
        return jsonObject;
    }

    @ApiOperation("微信-更新用户信息")
    @RequestMapping(value = "updateWxUser", method = RequestMethod.POST)
    public Object updateWxUser(@RequestBody WxUserDto wxUserDto, HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        try {
            WxUser wxUser = wxLoginService.queryById(wxUserDto.getId());
            if (null == wxUser) {
                jsonObject.put("code", ResponseCode.NODATA.getCode());
                jsonObject.put("msg", ResponseCode.NODATA.getMsg());
                return jsonObject;
            }
            wxUser.setRealName(wxUserDto.getRealName());
            wxUser.setPhone(wxUserDto.getPhone());
            wxLoginService.update(wxUser);
            jsonObject.put("code", ResponseCode.SUCCESS.getCode());
            jsonObject.put("msg", ResponseCode.SUCCESS.getMsg());
        } catch (Exception e) {
            jsonObject.put("code", ResponseCode.FAIL.getCode());
            jsonObject.put("msg", ResponseCode.FAIL.getMsg());
            log.error("wxUserLogin fail {}", e);
            return jsonObject;
        }
        return jsonObject;
    }

    @ApiOperation("微信-分销员信息")
    @RequestMapping(value = "getDistributionUser", method = RequestMethod.POST)
    public Object getDistributionUser(@RequestBody WxUserDto wxUserDto){
        JSONObject jsonObject = new JSONObject();
        try {
            WxUser wxUser = wxLoginService.queryById(wxUserDto.getId());
            jsonObject.put("code", ResponseCode.SUCCESS.getCode());
            jsonObject.put("msg", ResponseCode.SUCCESS.getMsg());
            jsonObject.put("data", wxUser);
        } catch (Exception e) {
            jsonObject.put("code", ResponseCode.FAIL.getCode());
            jsonObject.put("msg", ResponseCode.FAIL.getMsg());
            log.error("wxUserLogin fail {}", e);
            return jsonObject;
        }
        return jsonObject;
    }
}
