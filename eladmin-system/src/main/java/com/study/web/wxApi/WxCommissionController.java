package com.study.web.wxApi;

import com.study.web.dto.CommissionDto;
import com.study.web.dto.ResultValue;
import com.study.web.dto.TableResultValue;
import com.study.web.service.WxCommissionService;
import com.study.web.util.ParamCheckTool;
import com.study.web.util.ResponseCode;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
public class WxCommissionController extends JsonResultController{
    @Autowired
    private WxCommissionService wxCommissionService;

    @ApiOperation("微信-获取佣金列表")
    @RequestMapping(value = "/getCommissionList", method = RequestMethod.GET)
    public TableResultValue getCommissionList(HttpServletRequest request, HttpServletResponse response,@RequestBody CommissionDto commissionDto) {
        List<String> params = new ArrayList<>();
        params.add("wxUserId");
        ResultValue resultValue = ParamCheckTool.checkParam(params, commissionDto);
        if (resultValue != null) {
            return new TableResultValue(resultValue);
        }
        try {
            setPageInfo(commissionDto);
            List<CommissionDto> courseInfoList = new ArrayList<>();
            int total = wxCommissionService.totalList(commissionDto);
            if(total>0){
                courseInfoList = wxCommissionService.queryList(commissionDto);
            }
            return tableJsonResult(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg(),total,courseInfoList);
        } catch (Exception e) {
            log.error("getCommissionList fail {}", e);
            return errorTableResult( ResponseCode.FAIL.getCode(), ResponseCode.FAIL.getMsg());
        }
    }
}
