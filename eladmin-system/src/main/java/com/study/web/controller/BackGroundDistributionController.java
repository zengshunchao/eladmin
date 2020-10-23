package com.study.web.controller;

import com.study.annotation.Log;
import com.study.utils.PageUtil;
import com.study.web.dto.BackGroundDistributionInfoDto;
import com.study.web.dto.BackGroundOrderInfoDto;
import com.study.web.dto.BackGroundOrderQueryDto;
import com.study.web.service.BackGroundDistributionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
 * @author zsc
 * @date 2020/10/23 0023 20:28
 */
@Api(tags = "分销管理：分销列表管理")
@RestController
@RequestMapping("/background/distribution")
@Slf4j
public class BackGroundDistributionController {

    @Autowired
    private BackGroundDistributionService backGroundDistributionService;

    @Log("查询分销列表")
    @ApiOperation("查询分销列表")
    @GetMapping
    @PreAuthorize("@el.check('distribution:list')")
    public ResponseEntity<Object> queryDistributionList(BackGroundDistributionInfoDto infoDto, Pageable pageable) {
        try {
            int count = backGroundDistributionService.backGroundQueryDistributionTotal(infoDto);
            if (count > 0) {
                List<BackGroundDistributionInfoDto> backGroundDistributionInfoDtos =
                        backGroundDistributionService.backGroundQueryDistribution(infoDto, pageable);
                return new ResponseEntity<>(PageUtil.toPage(backGroundDistributionInfoDtos, count), HttpStatus.OK);
            }

        } catch (Exception e) {
            log.error("distribution query fail: ()", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(PageUtil.toPage(null, 0), HttpStatus.OK);
    }
}
