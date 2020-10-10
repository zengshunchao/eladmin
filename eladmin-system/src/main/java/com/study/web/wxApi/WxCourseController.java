package com.study.web.wxApi;

import com.alibaba.fastjson.JSONObject;
import com.study.web.dto.CourseInfoDto;
import com.study.web.service.WxCourseService;
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
import java.util.List;

/**
 * 微信外部接口
 */
@Slf4j
@RestController
@RequestMapping("/wxApi/course")
public class WxCourseController {

    @Autowired
    private WxCourseService wxCourseService;

    @ApiOperation("微信-获取课程列表")
    @RequestMapping(value = "/getCourseList", method = RequestMethod.GET)
    public Object getCourseList(HttpServletRequest request, HttpServletResponse response,
                                @RequestParam(value = "pageNo",defaultValue = "1") int pageNo,
                                @RequestParam(value = "pageSize",defaultValue = "10")int pageSize) {

        JSONObject jsonObject = new JSONObject();
        try {
            List<CourseInfoDto> courseInfoList = wxCourseService.queryList((pageNo - 1) * pageSize, pageSize);
            int total = wxCourseService.totalList();
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

    @ApiOperation("微信-根据id获取课程详情")
    @RequestMapping(value = "/getCourseInfoById", method = RequestMethod.GET)
    public Object getCourseInfo(HttpServletRequest request, HttpServletResponse response, Long id) {
        JSONObject jsonObject = new JSONObject();
        try {
            CourseInfoDto courseInfoDto = wxCourseService.queryCourseById(id);
            jsonObject.put("code", ResponseCode.SUCCESS.getCode());
            jsonObject.put("msg", ResponseCode.SUCCESS.getMsg());
            jsonObject.put("data", courseInfoDto);
        } catch (Exception e) {
            jsonObject.put("code", ResponseCode.FAIL.getCode());
            jsonObject.put("msg", ResponseCode.FAIL.getMsg());
            log.error("getCourseInfoById fail {}", e);
            return jsonObject;
        }
        return jsonObject;
    }

}
