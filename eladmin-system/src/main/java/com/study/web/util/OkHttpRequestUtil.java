package com.study.web.util;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 请求工具类
 */
@Slf4j
public class OkHttpRequestUtil {
    /**
     * 定义全局默认编码格式
     */
    private static final String CHARSET_NAME = "UTF-8";


    /**
     * 功能说明：向指定URL发送GET方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL所代表远程资源的响应结果
     * @throws IOException
     */
    public static String sendGet(String url, String param) {

        String result = "";
        try {
            OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
            String urlNameString = url + "?" + param;

            Request req = new Request.Builder().url(urlNameString).build();
            Response response = okHttpClient.newCall(req).execute();
            if (response.isSuccessful()) {
                result = response.body().string();
            }

        } catch (Exception e) {
            log.error("OkHttp sendGet fail {}", e);
        }
        return result;
    }

    /**
     * 功能说明：向指定URL发送GET方法的请求
     *
     * @param url    发送请求的URL
     * @param params 请求参数
     * @return URL所代表远程资源的响应结果
     * @throws IOException
     */
    public static String sendGet(String url, Map<String, String> params, Long connectionTimeOut, Long readTimeOut) {

        String result = "";
        try {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(connectionTimeOut, TimeUnit.SECONDS)
                    .readTimeout(readTimeOut, TimeUnit.SECONDS)
                    .build();
            HttpUrl.Builder httpBuilder = HttpUrl.parse(url).newBuilder();
            if (params != null) {
                for (Map.Entry<String, String> param : params.entrySet()) {
                    httpBuilder.addQueryParameter(param.getKey(), param.getValue());
                }
            }
            Request request = new Request.Builder()
                    .url(httpBuilder.build())
                    .build();
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                result = response.body().string();
            }

        } catch (Exception e) {
            log.error("OkHttp sendGet fail {}", e);
        }
        return result;
    }

    /**
     * 功能说明：向指定URL发送GET方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL所代表远程资源的响应结果
     * @throws IOException
     */
    public static String sendGet(String url, String param, Long connectionTimeOut, Long readTimeOut) throws IOException {
        String result = "";
        try {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(connectionTimeOut, TimeUnit.SECONDS)
                    .readTimeout(readTimeOut, TimeUnit.SECONDS)
                    .build();
            String urlNameString = url + "?" + param;

            Request req = new Request.Builder().url(urlNameString).build();
            Response response = okHttpClient.newCall(req).execute();
            if (response.isSuccessful()) {
                result = response.body().string();
            }
        } catch (Exception e) {
            log.error("OkHttp sendGet fail {}", e);
        }
        return result;
    }

    /**
     * 功能说明：向指定URL发送POST方法的请求
     *
     * @param url      发送请求的URL
     * @param jsonData 请求参数，请求参数应该是Json格式字符串的形式。
     * @return URL所代表远程资源的响应结果
     * @throws IOException
     */
    public static String sendPost(String url, String jsonData, Long connectionTimeOut, Long readTimeOut) {
        String result = "";
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(connectionTimeOut, TimeUnit.SECONDS)
                .readTimeout(readTimeOut, TimeUnit.SECONDS)
                .build();
        try {
            RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), jsonData);
            Request req = new Request.Builder()
                    .url(url)
                    .header("Content-Type", "application/json")
                    .post(body)
                    .build();
            Response response = okHttpClient.newCall(req).execute();
            if (response.isSuccessful()) {
                result = response.body().string();
            }
        } catch (Exception e) {
            log.error("OkHttp sendPost fail {}", e);
        }
        return result;
    }

    /**
     * 功能说明：向指定URL发送POST方法的请求
     *
     * @param url      发送请求的URL
     * @param jsonData 请求参数，请求参数应该是Json格式字符串的形式。
     * @return URL所代表远程资源的响应结果
     */
    public static String sendPost(String url, String jsonData) {
        String result = "";
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        try {
            RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), jsonData);
            Request req = new Request.Builder()
                    .url(url)
                    .header("Content-Type", "application/json")
                    .post(body)
                    .build();
            Response response = okHttpClient.newCall(req).execute();
            if (response.isSuccessful()) {
                result = response.body().string();
            }
        } catch (Exception e) {
            log.error("OkHttp sendPost fail {}", e);
        }
        return result;
    }
}
