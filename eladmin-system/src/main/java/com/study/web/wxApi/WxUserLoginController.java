package com.study.web.wxApi;

import com.alibaba.fastjson.JSONObject;
import com.study.web.dto.DistributionDto;
import com.study.web.dto.ResultValue;
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
public class WxUserLoginController extends JsonResultController{

    @Autowired
    private WxLoginService wxLoginService;

    @ApiOperation("微信-授权登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultValue wxUserLogin(@RequestBody WxUserDto wxUserDto, HttpServletRequest request, HttpServletResponse response) {
        try {
            WxUser wxUser = wxLoginService.wxLogin(wxUserDto);
            if (wxUser == null) {
                return errorResult( ResponseCode.FAIL.getCode(), "获取微信用户信息失败");
            }
            return jsonResult(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg(),wxUser);
        } catch (Exception e) {
            log.error("wxUserLogin fail {}", e);
            return errorResult( ResponseCode.FAIL.getCode(), ResponseCode.FAIL.getMsg());
        }
    }

    @ApiOperation("微信-更新用户信息")
    @RequestMapping(value = "updateWxUser", method = RequestMethod.POST)
    public ResultValue updateWxUser(@RequestBody WxUserDto wxUserDto, HttpServletRequest request, HttpServletResponse response) {
        try {
            WxUser wxUser = wxLoginService.queryById(wxUserDto.getId());
            if (null == wxUser) {
                return errorResult( ResponseCode.NODATA.getCode(), ResponseCode.NODATA.getMsg());
            }
            wxUser.setRealName(wxUserDto.getRealName());
            wxUser.setPhone(wxUserDto.getPhone());
            wxLoginService.update(wxUser);
            return successResult(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg());
        } catch (Exception e) {
            log.error("updateWxUser fail {}", e);
            return errorResult( ResponseCode.FAIL.getCode(), ResponseCode.FAIL.getMsg());
        }
    }

    @ApiOperation("微信-分销员信息")
    @RequestMapping(value = "getDistributionUser", method = RequestMethod.POST)
    public ResultValue getDistributionUser(@RequestBody WxUserDto wxUserDto){
        try {
            WxUser wxUser = wxLoginService.queryById(wxUserDto.getId());
            return jsonResult(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg(),wxUser);
        } catch (Exception e) {
            log.error("wxUserLogin fail {}", e);
            return errorResult( ResponseCode.FAIL.getCode(), ResponseCode.FAIL.getMsg());
        }
    }
}
