package com.study.web.util;

import com.alibaba.fastjson.JSONObject;
import com.study.exception.BadRequestException;
import com.study.utils.RsaUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ApiAuthenticationInterceptor extends HandlerInterceptorAdapter {

    public ApiAuthenticationInterceptor() {
        super();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String appId = request.getHeader("appId");
//        String source = request.getHeader("source");
//        String version = request.getHeader("version");
//        String signature = request.getHeader("signature");
//        if (StringUtils.isEmpty(signature) || StringUtils.isEmpty(appId)
//                || StringUtils.isEmpty(source) || StringUtils.isEmpty(version)) {
//            JSONObject jb = new JSONObject();
//            jb.put("code", ResponseCode.BADREQUESTPARAM.getCode());
//            jb.put("msg", ResponseCode.BADREQUESTPARAM.getMsg());
//            returnData(response, jb);
//            return false;
//        }
//        // 解密并校验
//        JSONObject jsonObject = checkParams(appId, source, version, signature);
//        if (!jsonObject.isEmpty()) {
//            returnData(response, jsonObject);
//            return false;
//        }
        return true;
    }

    /**
     * 解密参数并校验
     *
     * @param signature
     * @return
     */
    private JSONObject checkParams(String appId, String source, String version, String signature) {
        JSONObject jb = new JSONObject();
        try {
            if (!Constants.api_appId.equals(appId)) {
                jb.put("code", ResponseCode.BADREQUESTPARAM.getCode());
                jb.put("msg", ResponseCode.BADREQUESTPARAM.getMsg() + "appId");
                return jb;
            }
            if (!Constants.api_source.equals(source)) {
                jb.put("code", ResponseCode.BADREQUESTPARAM.getCode());
                jb.put("msg", ResponseCode.BADREQUESTPARAM.getMsg() + "source");
                return jb;
            }
            if (!Constants.api_version.equals(version)) {
                jb.put("code", ResponseCode.BADREQUESTPARAM.getCode());
                jb.put("msg", ResponseCode.BADREQUESTPARAM.getMsg() + "version");
                return jb;
            }
            String encryptText = "appId=%s&source=%s&version=%s";
            String format = String.format(encryptText, appId, source, version);

            // 解密
            String text = RsaUtils.decryptByPublicKey(Constants.api_publicKey, signature);
            if (StringUtils.isEmpty(text)) {
                jb.put("code", ResponseCode.BADREQUESTPARAM.getCode());
                jb.put("msg", ResponseCode.BADREQUESTPARAM.getMsg() + "signature");
                return jb;
            }
            if (!text.equals(format)) {
                jb.put("code", ResponseCode.BADREQUESTPARAM.getCode());
                jb.put("msg", "签名校验失败");
                return jb;
            }
        } catch (Exception e) {
            jb.put("code", ResponseCode.BADREQUESTPARAM.getCode());
            jb.put("msg", "解密失败");
            return jb;
        }
        return jb;
    }

    private void returnData(HttpServletResponse response, JSONObject errorResult) {
        try {
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().write(errorResult.toJSONString());
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        String encryptText = "appId=%s&source=%s&version=%s";
        String format = String.format(encryptText, Constants.api_appId, Constants.api_source, Constants.api_version);
        String s = RsaUtils.encryptByPrivateKey(Constants.api_privateKey, format);
        System.out.println(s);
    }
}
