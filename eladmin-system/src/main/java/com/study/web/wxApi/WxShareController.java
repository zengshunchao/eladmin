package com.study.web.wxApi;

import com.alibaba.fastjson.JSONObject;
import com.study.web.dto.CourseInfoDto;
import com.study.web.entity.Share;
import com.study.web.service.WxShareService;
import com.study.web.util.ResponseCode;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 微信-分享
 */
@Slf4j
@RestController
@RequestMapping("/wxApi/share")
public class WxShareController {

    @Autowired
    private WxShareService wxShareService;

    @ApiOperation("微信-根据微信用户id变更分享人")
    @RequestMapping(value = "/updateShareId", method = RequestMethod.POST)
    public Object updateShareId(HttpServletRequest request, HttpServletResponse response, Share share) {
        JSONObject jsonObject = new JSONObject();
        try {
            wxShareService.update(share);
            jsonObject.put("code", ResponseCode.SUCCESS.getCode());
            jsonObject.put("msg", ResponseCode.SUCCESS.getMsg());
        } catch (Exception e) {
            jsonObject.put("code", ResponseCode.FAIL.getCode());
            jsonObject.put("msg", ResponseCode.FAIL.getMsg());
            log.error("updateShareId fail {}", e);
            return jsonObject;
        }
        return jsonObject;
    }
}
