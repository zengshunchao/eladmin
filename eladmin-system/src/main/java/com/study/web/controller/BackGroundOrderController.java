package com.study.web.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.study.annotation.Log;
import com.study.utils.PageUtil;
import com.study.web.dto.*;
import com.study.web.entity.Order;
import com.study.web.service.BackGroundOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
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
    @PreAuthorize("@el.check('order:search')")
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
    @PreAuthorize("@el.check('order:check')")
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

    /**
     * 导出订单-excel
     *
     * @param request
     * @param response
     */
    @ApiOperation("导出订单")
    @GetMapping("/exportExcel")
    @PreAuthorize("@el.check('order:export')")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) {
        // 查询订单
        List<ExportOrderInfoDto> exportOrderInfoDtos = backGroundOrderService.exportOrderExcel();
        // 生成excel
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("订单", "sheet"), ExportOrderInfoDto.class, exportOrderInfoDtos);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            workbook.write(os);
            byte[] bytes = os.toByteArray();
            String fileName = URLEncoder.encode("订单统计.xlsx", "utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            response.getOutputStream().write(bytes);
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (Exception e) {
            log.error("exportExcel fail {}", e);
        }
    }
}
