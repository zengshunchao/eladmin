package com.study.web.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.study.annotation.Log;
import com.study.service.LocalStorageService;
import com.study.utils.PageUtil;
import com.study.web.dto.CourseInfoDto;
import com.study.web.dto.CourseQueryDto;
import com.study.web.entity.Course;
import com.study.web.entity.Picture;
import com.study.web.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;

/**
 * (Course)表控制层
 *
 * @author zengsc
 * @since 2020-09-03 15:47:48
 */
@Api(tags = "课程管理：课程列表管理")
@RestController
@RequestMapping("/background/course")
@Slf4j
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Log("查询课程")
    @ApiOperation("查询课程")
    @GetMapping
    @PreAuthorize("@el.check('user:list')")
    public ResponseEntity<Object> queryList(CourseQueryDto course, Pageable pageable) {
        try {
            int total = courseService.totalCourse(course);
            if (total > 0) {
                List<CourseInfoDto> courses = courseService.queryAll(course, pageable);
                return new ResponseEntity<>(PageUtil.toPage(courses, total), HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("course query fail: ()", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(PageUtil.toPage(null, 0), HttpStatus.OK);
    }

    @ApiOperation("添加课程")
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @PreAuthorize("@el.check('course:add')")
    public ResponseEntity<Object> create(Course course,
                                         @RequestParam("picture") String picture,
                                         HttpServletRequest request,
                                         HttpServletResponse response) {

        if (StringUtils.isEmpty(picture)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Picture> pictureList = JSONObject.parseArray(picture, Picture.class);
        if (CollectionUtil.isEmpty(pictureList)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            courseService.insert(course, pictureList);
        } catch (Exception e) {
            log.error("course insert fail: ()", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("删除课程")
    @DeleteMapping
    @PreAuthorize("@el.check('course:del')")
    public ResponseEntity<Object> delete(@RequestBody Set<Long> ids) {
        try {
            courseService.deleteBatch(ids);
        } catch (Exception e) {
            log.error("deleteBatch course fail: ()", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("更新课程状态")
    @GetMapping("/updateLineType/{id}")
    public ResponseEntity<Object> updateLineType(@PathVariable("id") Long id) {
        try {
            courseService.updateLineType(id);
        } catch (Exception e) {
            log.error("course updateLineType fail: ()", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("更新课程")
    @PostMapping("updateCourse")
    public ResponseEntity<Object> updateCourse(Course course, @RequestParam("picture") String picture) {
        try {
            if (null == course.getId()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            if (StringUtils.isEmpty(picture)) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            List<Picture> pictureList = JSONObject.parseArray(picture, Picture.class);
            if (CollectionUtil.isEmpty(pictureList)) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            courseService.update(course, pictureList);
        } catch (Exception e) {
            log.error("course updateLineType fail: ()", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("修改排序")
    @RequestMapping("/updateSortNumber")
    public ResponseEntity<Object> updateSortNumber(@RequestParam(value = "id") Long id, @RequestParam(value = "sortNumber") Integer sortNumber) {
        try {
            Course course = new Course();
            if (null == id) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            course.setId(id);
            course.setSortNumber(sortNumber);
            if (null == sortNumber) {
                course.setSortNumber(100);
            }
            courseService.updateSortNumber(course);
        } catch (Exception e) {
            log.error("course updateSortNumber fail: ()", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
