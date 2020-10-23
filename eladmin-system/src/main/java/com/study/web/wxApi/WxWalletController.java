package com.study.web.wxApi;

import com.alibaba.fastjson.JSONObject;
import com.study.web.dto.ResultValue;
import com.study.web.dto.WalletDto;
import com.study.web.entity.Wallet;
import com.study.web.service.WxWalletService;
import com.study.web.util.ResponseCode;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
            //wxWalletService.withdrawal(walletDto);
            //return successResult(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg());

            return errorResult( ResponseCode.FAIL.getCode(), ResponseCode.FAIL.getMsg());
        } catch (Exception e) {
            log.error("getWallet fail {}", e);
            return errorResult( ResponseCode.FAIL.getCode(), ResponseCode.FAIL.getMsg());
        }
    }
}
