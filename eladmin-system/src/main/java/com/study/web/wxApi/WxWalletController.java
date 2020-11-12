package com.study.web.wxApi;

import com.alibaba.fastjson.JSONObject;
import com.study.web.dto.ResultValue;
import com.study.web.dto.TableResultValue;
import com.study.web.dto.WalletDto;
import com.study.web.dto.WalletWaterDto;
import com.study.web.entity.Wallet;
import com.study.web.entity.WalletWater;
import com.study.web.service.WxWalletService;
import com.study.web.service.wxImpl.WxWalletWaterServiceImpl;
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
 * @date 2020/10/12
 */
@Slf4j
@RestController
@RequestMapping("/wxApi/wallet")
public class WxWalletController extends JsonResultController{
    @Autowired
    private WxWalletService wxWalletService;

    @Autowired
    private WxWalletWaterServiceImpl wxWalletWaterService;

    @ApiOperation("钱包")
    @RequestMapping(value = "getWallet", method = RequestMethod.POST)
    public ResultValue getWallet(@RequestBody Wallet wallet){
        try {
            wallet =  wxWalletService.queryByWxUserId(wallet.getWxUserId());
            return jsonResult(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg(),wallet);
        } catch (Exception e) {
            log.error("getWallet fail {}", e);
            return errorResult( ResponseCode.FAIL.getCode(), ResponseCode.FAIL.getMsg());
        }
    }

    @ApiOperation("提现")
    @RequestMapping(value = "withdrawal", method = RequestMethod.POST)
    public ResultValue withdrawal(@RequestBody WalletDto walletDto){
        try {
            wxWalletService.withdrawal(walletDto);
            return successResult(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg());
        } catch (Exception e) {
            log.error("getWallet fail {}", e);
            return errorResult( ResponseCode.FAIL.getCode(), ResponseCode.FAIL.getMsg());
        }
    }

    @ApiOperation("钱包流水")
    @RequestMapping(value = "getWalletWaterList", method = RequestMethod.POST)
    public TableResultValue getWalletWaterList(@RequestBody WalletWaterDto walletWaterDto){
        try {
            Integer count = wxWalletWaterService.countWalletWaterList(walletWaterDto);
            List<WalletWater> list = new ArrayList<>();
            if(count>0){
                list = wxWalletWaterService.getWalletWaterList(walletWaterDto);
            }
            return tableJsonResult(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg(),count,list);
        } catch (Exception e) {
            log.error("getWallet fail {}", e);
            return errorTableResult( ResponseCode.FAIL.getCode(), ResponseCode.FAIL.getMsg());
        }
    }

}
