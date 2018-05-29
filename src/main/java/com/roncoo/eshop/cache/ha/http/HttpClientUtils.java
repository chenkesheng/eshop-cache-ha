package com.roncoo.eshop.cache.ha.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @Author: cks
 * @Date: Created by 14:12 2018/5/23
 * @Package: com.roncoo.eshop.cache.ha.http
 * @Description: HttpClient工具类
 */
public class HttpClientUtils {
    /**
     * 发送GET请求
     *
     * @param url 请求URL
     * @return 响应结果
     */
    @SuppressWarnings("resource")
    public static String sendGetRequest(String url) {
        String httpResponse = null;

        HttpClient httpclient = null;
        InputStream is = null;
        BufferedReader br = null;

        try {
            // 发送GET请求
            httpclient = HttpClientBuilder.create().build();
            HttpGet httpget = new HttpGet(url);
            HttpResponse response = httpclient.execute(httpget);

            // 处理响应
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                is = entity.getContent();
                br = new BufferedReader(new InputStreamReader(is));

                StringBuffer buffer = new StringBuffer();
                String line;

                while ((line = br.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                httpResponse = buffer.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        return httpResponse;
    }

    /**
     * 发送post请求
     *
     * @param url URL
     * @param map 参数Map
     * @return
     */
    public static String sendPostRequest(String url, Map<String, String> map) {
        HttpClient httpClient;
        HttpPost httpPost;
        String result = null;

        try {
            httpClient = HttpClientBuilder.create().build();
            httpPost = new HttpPost(url);

            //设置参数
            List<NameValuePair> list = new ArrayList<>();
            Iterator iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String, String> elem = (Entry<String, String>) iterator.next();
                list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
            }
            if (list.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "utf-8");
                httpPost.setEntity(entity);
            }

            HttpResponse response = httpClient.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, "utf-8");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {

        }
        return result;
    }
}
