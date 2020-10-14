package com.study.web.wxApi;

import com.study.web.dto.DistributionDto;
import com.study.web.dto.ResultValue;
import com.study.web.dto.TableResultValue;
import com.study.web.dto.WxUserDto;
import com.study.web.entity.Distribution;
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
public class WxDistributionController extends JsonResultController {
    @Autowired
    private WxDistributionService wxDistributionService;

    @ApiOperation("添加分销员")
    @RequestMapping(value = "addDistribution", method = RequestMethod.POST)
    public ResultValue addDistribution(@RequestBody Distribution distribution){
        try {
            wxDistributionService.addDistribution(distribution);
            return successResult(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg());
        } catch (Exception e) {
            log.error("addDistribution fail {}", e);
            return errorResult( ResponseCode.FAIL.getCode(), ResponseCode.FAIL.getMsg());
        }
    }

    @ApiOperation("下级分销员")
    @RequestMapping(value = "getDistributionList", method = RequestMethod.POST)
    public TableResultValue getDistributionList(@RequestBody DistributionDto distributionDto){
        try {
            setPageInfo(distributionDto);
            List<WxUserDto> wxUserDtoList = new ArrayList<>();
            int total = wxDistributionService.totalList(distributionDto);
            if(total>0){
                wxUserDtoList = wxDistributionService.getDistributionList(distributionDto);
            }
            return tableJsonResult(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg(),total,wxUserDtoList);
        } catch (Exception e) {
            log.error("getDistributionList fail {}", e);
            return errorTableResult( ResponseCode.FAIL.getCode(), ResponseCode.FAIL.getMsg());
        }
    }
}
