package com.study.web.controller;

import com.study.annotation.Log;
import com.study.utils.PageUtil;
import com.study.web.dto.CourseInfoDto;
import com.study.web.dto.CourseQueryDto;
import com.study.web.dto.WxUserDto;
import com.study.web.entity.WxUser;
import com.study.web.service.WxUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 后台管理-微信用户相关
 */
@Api(tags = "课程管理：课程列表管理")
@RestController
@RequestMapping("/background/wxUser")
@Slf4j
public class UserController {

    @Autowired
    private WxUserService wxUserService;

    @Log("查询微信用户")
    @ApiOperation("查询微信用户")
    @GetMapping
    @PreAuthorize("@el.check('wxUser:list')")
    public ResponseEntity<Object> queryList(WxUserDto wxUserDto, Pageable pageable) {
        try {
            List<WxUserDto> wxUserDtoList = wxUserService.queryAll(wxUserDto);
            //查询用户列表
            if (wxUserDtoList != null) {
                int page = wxUserDtoList.size();
                wxUserDtoList = PageUtil.toPage(pageable.getPageNumber(), pageable.getPageSize(), wxUserDtoList);
                for (WxUserDto info : wxUserDtoList) {
//                    if (null != info.getParentId()) {
//                        WxUser user = wxUserService.queryById(info.getParentId());
//                        info.setParentName(user.getNickName());
//                    }
                }
                return new ResponseEntity<>(PageUtil.toPage(wxUserDtoList, page), HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("wxUser query fail: ()", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(PageUtil.toPage(null, 0), HttpStatus.OK);
    }
}
