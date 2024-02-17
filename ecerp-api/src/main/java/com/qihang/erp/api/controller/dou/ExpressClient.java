package com.qihang.erp.api.controller.dou;



import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description: pbd add 2019/6/13 16:00
 */
public class ExpressClient {
    private static Logger log = LoggerFactory.getLogger(ExpressClient.class);
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static String key = "Ywq0uOo6nTEx";
    private static String serret = "6880dfb586d1442a9bbfd523fc30127f";

    /**
     * post请求
     *
     * @param sendUrl 请求地址
     * @param params  请求参数
     */
    public static HttpResponse<String> doPost(String sendUrl, String params) {
        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(sendUrl)).header("Content-Type", "application/x-www-form-urlencoded").POST(HttpRequest.BodyPublishers.ofString(params))
                    .build();
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            log.error("ExpressClient doPost exception:" + e);
        }
        return null;

    }

    /**
     * 参数拼接，字符加密
     *
     * @param params
     * @param clientSecret
     * @return
     */
    public static String buildSign(Map<String, String> params, String
            clientSecret) {
        Map<String, String> paramsMap = null;
        if (params instanceof TreeMap) {
            paramsMap = params;
        } else {
            paramsMap = new TreeMap<String, String>();
            paramsMap.putAll(params);//加入所有参数
        }
        StringBuffer strBuffer = new StringBuffer(clientSecret);
        Set<Map.Entry<String, String>> entrySet = paramsMap.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            strBuffer.append(entry.getKey()).append(entry.getValue());
        }
        strBuffer.append(clientSecret);
        return Md5Util.MD5(strBuffer.toString());
    }

    public static String buildSignObj(Map<String, Object> params, String
            clientSecret) {
        Map<String, Object> paramsMap = null;
        if (params instanceof TreeMap) {
            paramsMap = params;
        } else {
            paramsMap = new TreeMap<String, Object>();
            paramsMap.putAll(params);//加入所有参数
        }
        StringBuffer strBuffer = new StringBuffer(clientSecret);
        Set<Map.Entry<String, Object>> entrySet = paramsMap.entrySet();
        for (Map.Entry<String, Object> entry : entrySet) {
            strBuffer.append(entry.getKey()).append(entry.getValue());
            System.out.println(entry.getValue());
        }
        strBuffer.append(clientSecret);
        return Md5Util.MD5(strBuffer.toString());
    }
    public static String buildSign2(Map<String, Object> params, String clientSecret) {
        Map<String, Object> newMap=new HashMap<>();
        Set<Map.Entry<String, Object>> newSet = params.entrySet();
        for (Map.Entry<String, Object> entry : newSet) {
            if(entry.getValue().getClass().equals(JSONObject.class)){
                JSONObject objJson = JSON.parseObject(entry.getValue().toString());
                newMap.put(entry.getKey(),"");
                Iterator<String> it = objJson.keySet().iterator();
                while(it.hasNext()){
                    String key = it.next();
                    if (objJson.get(key) instanceof JSONObject) {
                        getJSONObject((JSONObject)objJson.get(key),newMap);
                    } else if (objJson.get(key) instanceof JSONArray) {
                        newMap.put(key,"");
                        getJSONArray((JSONArray)objJson.get(key),newMap);
                    }
                }
            }
            if(entry.getValue() instanceof String ){
                newMap.put(entry.getKey(),entry.getValue());
            }
        }

        Map<String, Object> paramsMap = null;
        if (newMap instanceof TreeMap) {
            paramsMap = newMap;
        } else {
            paramsMap = new TreeMap<>();
            paramsMap.putAll(newMap);//加入所有参数
        }
        StringBuffer strBuffer = new StringBuffer(clientSecret);
        Set<Map.Entry<String, Object>> entrySet = paramsMap.entrySet();
        for (Map.Entry<String, Object> entry : entrySet) {
            System.out.println(entry.getKey());
            strBuffer.append(entry.getKey()).append(entry.getValue().toString());
/*            StringBuffer buffer = new StringBuffer();
            if(entry.getValue().getClass().equals(JSONObject.class)){
                JSONObject objJson = JSON.parseObject(entry.getValue().toString());
                buffer.append(entry.getKey());
                Iterator<String> it = objJson.keySet().iterator();
                while(it.hasNext()){
                    String key = it.next();
                    if (objJson.get(key) instanceof JSONObject) {
                        buffer.append(key).append(objJson.getString(key));
                    } else if (objJson.get(key) instanceof JSONArray) {
                        buffer.append(key);
                        JSONArray arrayObj = (JSONArray) objJson.get(key);
                        buffer.append(getJSONArray(arrayObj));
                    }
                }
                System.out.println(buffer.toString());
            }
            strBuffer.append(buffer);
            if(entry.getValue() instanceof String ){
                System.out.println(entry.getKey());
                strBuffer.append(entry.getKey()).append(entry.getValue());
            }*/
        }
        strBuffer.append(clientSecret);
        return Md5Util.MD5(strBuffer.toString());
    }

    public static StringBuffer getJSONArray(JSONArray json,Map<String, Object> map) {
        StringBuffer stringBuffer = new StringBuffer();
        if (json != null ) {
            Iterator i1 = json.iterator();
            while (i1.hasNext()) {
                Object key = i1.next();
                if (key instanceof  JSONObject) {
                    JSONObject innerObject = (JSONObject) key;
                    getJSONObject(innerObject,map);
                    //stringBuffer.append(getJSONObject(innerObject));
                }
        }
    }
        return stringBuffer;
    }

    public static StringBuffer getJSONObject(JSONObject json,Map<String, Object> map) {
        StringBuffer stringBuffer = new StringBuffer();
        if (json != null ) {
            Iterator<String> it = json.keySet().iterator();
            while(it.hasNext()){
                String key = it.next();
                if (json.get(key) instanceof String) {
                    //stringBuffer.append(key).append(json.getString(key));
                    map.put(key,json.getString(key));
                }
            }
        }
        return stringBuffer;
    }


    /**
     * 对字节数组字符串进行Base64解码并生成图片
     *
     * @param base64
     * @param path
     * @return
     * @throws IOException
     */
    public static boolean base64ToImageFile(String base64, String path) throws IOException {
        System.out.println(base64.length());
        // 生成jpeg图片
        try {
            OutputStream out = new FileOutputStream(path);
            return base64ToImageOutput(base64, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 处理Base64解码并输出流
     *
     * @param base64
     * @param out
     * @return
     */
    public static boolean base64ToImageOutput(String base64, OutputStream out) throws IOException {
        if (base64 == null) { // 图像数据为空
            return false;
        }
        try {
            // Base64解码
            byte[] bytes = org.apache.commons.codec.binary.Base64.decodeBase64(base64);
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] < 0) {// 调整异常数据
                    bytes[i] += 256;
                }
            }
            // 生成jpeg图片
            out.write(bytes);
            out.flush();
            return true;
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
