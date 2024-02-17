package com.qihang.erp.api.controller.dou;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

/**
 * @Description: http 请求工具类
 * pbd add 2019/7/1 14:29
 */
public class HttpUtil {
    private static Logger log = LoggerFactory.getLogger(ExpressClient.class);
    private static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * post请求
     *
     * @param sendUrl 请求地址
     * @param params  请求参数
     */
    public static HttpResponse<String> doPost(String sendUrl, String params) {
        try {
            java.net.http.HttpClient client = java.net.http.HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(sendUrl)).header("Content-Type", "application/x-www-form-urlencoded").POST(HttpRequest.BodyPublishers.ofString(params))
                    .build();
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            log.error("HttpClient doPost exception:" + e);
        }
        return null;

    }

    public static HttpResponse<String> doPostJson(String sendUrl, String params) {
        try {
            java.net.http.HttpClient client = java.net.http.HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(sendUrl)).header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(params))
                    .build();
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            log.error("HttpClient doPost exception:" + e);
        }
        return null;

    }

    /**
     * 请求参数拼接
     *
     * @param paramToMap
     * @return
     * @throws Exception
     */
    public static String map2Url(Map<String, String> paramToMap) throws Exception {
        if (null == paramToMap || paramToMap.isEmpty()) {
            return null;
        }
        StringBuffer url = new StringBuffer();
        boolean isfist = true;
        for (Map.Entry<String, String> entry : paramToMap.entrySet()) {
            if (isfist) {
                isfist = false;
            } else {
                url.append("&");
            }
            url.append(entry.getKey()).append("=");
            String value = entry.getValue();
            if (!StringUtils.isEmpty(value)) {
                url.append(URLEncoder.encode(value, DEFAULT_CHARSET));
            }
        }
        return url.toString();
    }
    /**
     * 请求参数拼接
     *
     * @param map
     * @return
     * @throws Exception
     */
    public static String mapToUrl(Map<String, Object> map) throws Exception {
        if (null == map || map.isEmpty()) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        boolean isfist = true;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (isfist) {
                isfist = false;
            } else {
                sb.append("&");
            }
            sb.append(entry.getKey() + "=" +  URLEncoder.encode(entry.getValue().toString(), DEFAULT_CHARSET));
        }
        String s = sb.toString();
        //s = substringBeforeLast(s, "&");
        return s;
    }

    public static String substringBeforeLast(String str, String separator) {
        if (!StringUtils.isEmpty(str) && !StringUtils.isEmpty(separator)) {
            int pos = str.lastIndexOf(separator);
            return pos == -1 ? str : str.substring(0, pos);
        } else {
            return str;
        }
    }
}
