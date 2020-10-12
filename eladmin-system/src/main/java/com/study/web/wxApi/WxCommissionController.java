package com.study.web.wxApi;

import com.alibaba.fastjson.JSONObject;
import com.study.utils.StringUtils;
import com.study.web.dto.CommissionDto;
import com.study.web.service.WxCommissionService;
import com.study.web.util.ResponseCode;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zyf
 * @date 2020/10/11
 */
@Slf4j
@RestController
@RequestMapping("/wxApi/commission")
public class WxCommissionController {
    @Autowired
    private WxCommissionService wxCommissionService;

    @ApiOperation("微信-获取佣金列表")
    @RequestMapping(value = "/getCommissionList", method = RequestMethod.GET)
    public Object getCommissionList(HttpServletRequest request, HttpServletResponse response,CommissionDto commissionDto) {
        JSONObject jsonObject = new JSONObject();
        if(null == commissionDto.getWxUserId()){
            jsonObject.put("code", ResponseCode.BADREQUESTPARAM.getCode());
            jsonObject.put("msg", ResponseCode.BADREQUESTPARAM.getMsg());
            return jsonObject;
        }
        try {
            commissionDto.setPage();
            List<CommissionDto> courseInfoList = new ArrayList<>();
            int total = wxCommissionService.totalList(commissionDto);
            if(total>0){
                courseInfoList = wxCommissionService.queryList(commissionDto);
            }
            jsonObject.put("code", ResponseCode.SUCCESS.getCode());
            jsonObject.put("msg", ResponseCode.SUCCESS.getMsg());
            jsonObject.put("data", courseInfoList);
            jsonObject.put("total", total);
        } catch (Exception e) {
            jsonObject.put("code", ResponseCode.FAIL.getCode());
            jsonObject.put("msg", ResponseCode.FAIL.getMsg());
            log.error("getCourseList fail {}", e);
            return jsonObject;
        }
        return jsonObject;
    }
}
