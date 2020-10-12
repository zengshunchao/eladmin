package com.study.web.wxApi;

import com.alibaba.fastjson.JSONObject;
import com.study.web.dto.CourseInfoDto;
import com.study.web.dto.DistributionDto;
import com.study.web.dto.WxUserDto;
import com.study.web.entity.Distribution;
import com.study.web.entity.WxUser;
import com.study.web.service.WxDistributionService;
import com.study.web.util.ResponseCode;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zyf
 * @date 2020/10/11
 */
@Slf4j
@RestController
@RequestMapping("/wxApi/distribution")
public class WxDistributionController {
    @Autowired
    private WxDistributionService wxDistributionService;

    @ApiOperation("添加分销员")
    @RequestMapping(value = "addDistribution", method = RequestMethod.POST)
    public Object addDistribution(@RequestBody Distribution distribution){
        JSONObject jsonObject = new JSONObject();
        try {
            if(null == distribution.getParentId() || null == distribution.getWxUserId()){
                jsonObject.put("code", ResponseCode.BADREQUESTPARAM.getCode());
                jsonObject.put("msg", ResponseCode.BADREQUESTPARAM.getMsg());
                return jsonObject;
            }
            wxDistributionService.addDistribution(distribution);
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

    @ApiOperation("下级分销员")
    @RequestMapping(value = "getDistributionList", method = RequestMethod.POST)
    public Object getDistributionList(@RequestBody DistributionDto distributionDto){
        JSONObject jsonObject = new JSONObject();
        try {
            List<WxUserDto> wxUserDtoList = wxDistributionService.getDistributionList(distributionDto);
            jsonObject.put("code", ResponseCode.SUCCESS.getCode());
            jsonObject.put("msg", ResponseCode.SUCCESS.getMsg());
            jsonObject.put("data", wxUserDtoList);
        } catch (Exception e) {
            jsonObject.put("code", ResponseCode.FAIL.getCode());
            jsonObject.put("msg", ResponseCode.FAIL.getMsg());
            log.error("wxUserLogin fail {}", e);
            return jsonObject;
        }
        return jsonObject;
    }
}
