package com.study.web.wxApi;

import com.study.web.dto.ResultValue;
import com.study.web.entity.Share;
import com.study.web.service.WxShareService;
import com.study.web.util.ResponseCode;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 微信-分享
 */
@Slf4j
@RestController
@RequestMapping("/wxApi/share")
public class WxShareController extends JsonResultController{

    @Autowired
    private WxShareService wxShareService;

    @ApiOperation("微信-根据微信用户id变更分享人")
    @RequestMapping(value = "/updateShareId", method = RequestMethod.POST)
    public ResultValue updateShareId(HttpServletRequest request, HttpServletResponse response, @RequestBody Share share) {
        try {
            wxShareService.update(share);
            return successResult(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg());
        } catch (Exception e) {
            log.error("updateShareId fail {}", e);
            return errorResult( ResponseCode.FAIL.getCode(), ResponseCode.FAIL.getMsg());
        }
    }
}
