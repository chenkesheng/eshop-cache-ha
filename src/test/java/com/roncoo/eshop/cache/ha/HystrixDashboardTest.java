package com.roncoo.eshop.cache.ha;

import com.roncoo.eshop.cache.ha.http.HttpClientUtils;

/**
 * @Author: cks
 * @Date: Created by 17:01 2018/5/30
 * @Package: com.roncoo.eshop.cache.ha
 * @Description:
 */
public class HystrixDashboardTest {
    public static void main(String[] args) {
        HttpClientUtils.sendGetRequest("http://localhost:8084/getProductInfo?productId=1");
    }
}
