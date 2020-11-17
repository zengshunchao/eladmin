package com.study.web.controller;

import com.study.annotation.Log;
import com.study.utils.PageUtil;
import com.study.web.dto.ApplyDto;
import com.study.web.dto.CourseInfoDto;
import com.study.web.dto.CourseQueryDto;
import com.study.web.entity.Apply;
import com.study.web.entity.Course;
import com.study.web.entity.WxUser;
import com.study.web.service.ApplyService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * (Apply)表控制层
 *
 * @author zengsc
 * @since 2020-11-11 20:08:37
 */
@RestController
@RequestMapping("/background/apply")
@Slf4j
public class ApplyController {
    /**
     * 服务对象
     */
    @Autowired
    private ApplyService applyService;

    @Log("查询提现申请")
    @ApiOperation("查询提现申请")
    @GetMapping
    @PreAuthorize("@el.check('apply:list')")
    public ResponseEntity<Object> queryList(WxUser wxUser, Pageable pageable) {
        try {
            int total = applyService.totalCount(wxUser);
            if (total > 0) {
                List<ApplyDto> applies = applyService.queryAllByLimit(wxUser, pageable);
                return new ResponseEntity<>(PageUtil.toPage(applies, total), HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("apply query fail: ()", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(PageUtil.toPage(null, 0), HttpStatus.OK);
    }

    @ApiOperation("提现申请审批")
    @PostMapping("/checkApply")
    public ResponseEntity<Object> updateApplyStatus(@RequestBody Apply apply) {
        try {
            applyService.update(apply);
        } catch (Exception e) {
            log.error("updateApplyStatus  fail: ()", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Log("查询提现记录")
    @ApiOperation("查询提现记录")
    @GetMapping("/queryCommissionRecord")
    @PreAuthorize("@el.check('apply:list')")
    public ResponseEntity<Object> querySuccessList(WxUser wxUser, Pageable pageable) {
        try {
            int total = applyService.totalSuccessCount(wxUser);
            if (total > 0) {
                List<ApplyDto> applies = applyService.queryAllSuccessByLimit(wxUser, pageable);
                return new ResponseEntity<>(PageUtil.toPage(applies, total), HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("commissionRecord query fail: ()", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(PageUtil.toPage(null, 0), HttpStatus.OK);
    }

    @ApiOperation("查询平台累计提现金额")
    @PostMapping("getAllMoney")
    public ResponseEntity<Object> getAllMoney() {
        try {
            BigDecimal allMoney = applyService.getAllMoney();
            return new ResponseEntity<>(allMoney, HttpStatus.OK);
        } catch (Exception e) {
            log.error("getAllMoney fail: ()", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}