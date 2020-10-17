package com.study.web.controller;

import com.study.annotation.Log;
import com.study.utils.PageUtil;
import com.study.web.dto.BackGroundOrderInfoDto;
import com.study.web.dto.BackGroundOrderQueryDto;
import com.study.web.dto.CourseInfoDto;
import com.study.web.dto.CourseQueryDto;
import com.study.web.entity.Order;
import com.study.web.service.BackGroundOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zsc
 * @date 2020/10/15 0015 22:49
 */
@Api(tags = "课程管理：课程列表管理")
@RestController
@RequestMapping("/background/order")
@Slf4j
public class BackGroundOrderController {

    @Autowired
    private BackGroundOrderService backGroundOrderService;

    @Log("查询订单")
    @ApiOperation("查询订单")
    @GetMapping
    @PreAuthorize("@el.check('order:list')")
    public ResponseEntity<Object> queryList(BackGroundOrderQueryDto orderQueryDto, Pageable pageable) {
        try {
            int count = backGroundOrderService.totalOrder(orderQueryDto);
            if (count > 0) {
                List<BackGroundOrderInfoDto> backGroundOrderInfoDtos = backGroundOrderService.queryOrderByLimit(orderQueryDto, pageable);
                //查询订单列表
                return new ResponseEntity<>(PageUtil.toPage(backGroundOrderInfoDtos, count), HttpStatus.OK);
            }

        } catch (Exception e) {
            log.error("order query fail: ()", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(PageUtil.toPage(null, 0), HttpStatus.OK);
    }

    @ApiOperation("查看订单详情")
    @GetMapping("/getOrderById/{id}")
    public ResponseEntity<Object> getOrderById(@PathVariable("id") Long id) {
        try {
            BackGroundOrderInfoDto backGroundOrderInfoDto = backGroundOrderService.queryOrderById(id);
            return new ResponseEntity<>(backGroundOrderInfoDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("getOrderById fail: ()", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation("订单核销")
    @GetMapping("/updateCheckCode/{id}")
    public ResponseEntity<Object> updateCheckCode(@PathVariable("id") Long id) {
        try {
            if (id == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            Order order = new Order();
            order.setId(id);
            backGroundOrderService.updateCheckTimeAndStatus(order);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("updateCheckCode fail: ()", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
