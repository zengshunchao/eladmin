package com.study.web.wxApi;

import com.alibaba.fastjson.JSONObject;
import com.study.web.dto.CourseInfoDto;
import com.study.web.dto.ResultValue;
import com.study.web.dto.TableResultValue;
import com.study.web.service.WxCourseService;
import com.study.web.util.ResponseCode;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 微信外部接口
 */
@Slf4j
@RestController
@RequestMapping("/wxApi/course")
public class WxCourseController extends JsonResultController{

    @Autowired
    private WxCourseService wxCourseService;

    @ApiOperation("微信-获取课程列表")
    @RequestMapping(value = "/getCourseList", method = RequestMethod.POST)
    public TableResultValue getCourseList(HttpServletRequest request, HttpServletResponse response, @RequestBody CourseInfoDto courseInfoDto) {

        try {
            setPageInfo(courseInfoDto);
            List<CourseInfoDto> courseInfoList = wxCourseService.queryList(courseInfoDto);
            int total = wxCourseService.totalList();
            return tableJsonResult(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg(),total,courseInfoList);
        } catch (Exception e) {
            log.error("getCourseList fail {}", e);
            return errorTableResult( ResponseCode.FAIL.getCode(), ResponseCode.FAIL.getMsg());
        }
    }

    @ApiOperation("微信-根据id获取课程详情")
    @RequestMapping(value = "/getCourseInfoById", method = RequestMethod.GET)
    public ResultValue getCourseInfo(HttpServletRequest request, HttpServletResponse response, Long id) {
        try {
            CourseInfoDto courseInfoDto = wxCourseService.queryCourseById(id);
            return jsonResult(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg(),courseInfoDto);
        } catch (Exception e) {
            log.error("getCourseInfoById fail {}", e);
            return errorResult( ResponseCode.FAIL.getCode(), ResponseCode.FAIL.getMsg());
        }
    }

}
