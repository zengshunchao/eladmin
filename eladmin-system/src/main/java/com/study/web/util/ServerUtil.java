package com.study.web.util;

import javax.servlet.http.HttpServletRequest;

public class ServerUtil {

    /**
     * 获取服务器ip+端口，图片回调显示
     *
     * @param request
     * @return
     */
    public static String getServerIPPort(HttpServletRequest request) {

        return "https://" + request.getServerName() + ":" + request.getServerPort();
    }
}
