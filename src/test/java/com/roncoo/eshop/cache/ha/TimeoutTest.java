package com.roncoo.eshop.cache.ha;

import com.roncoo.eshop.cache.ha.http.HttpClientUtils;

/**
 * @Author: cks
 * @Date: Created by 13:43 2018/5/29
 * @Package: com.roncoo.eshop.cache.ha
 * @Description:
 */
public class TimeoutTest {
    public static void main(String[] args) throws Exception {
        HttpClientUtils.sendGetRequest("http://localhost:8084/getProductInfo?productId=-3");
    }

}
