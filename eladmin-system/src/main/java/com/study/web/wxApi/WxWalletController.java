package com.study.web.wxApi;

import com.alibaba.fastjson.JSONObject;
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
public class WxWalletController {
    @Autowired
    private WxWalletService wxWalletService;

    @ApiOperation("钱包")
    @RequestMapping(value = "getWallet", method = RequestMethod.POST)
    public Object getWallet(@RequestBody Wallet wallet){
        JSONObject jsonObject = new JSONObject();
        try {
            wallet =  wxWalletService.queryByWxUserId(wallet.getWxUserId());
            jsonObject.put("code", ResponseCode.SUCCESS.getCode());
            jsonObject.put("msg", ResponseCode.SUCCESS.getMsg());
            jsonObject.put("data", wallet);
        } catch (Exception e) {
            jsonObject.put("code", ResponseCode.FAIL.getCode());
            jsonObject.put("msg", ResponseCode.FAIL.getMsg());
            log.error("wxUserLogin fail {}", e);
            return jsonObject;
        }
        return jsonObject;
    }
}
