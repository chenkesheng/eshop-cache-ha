package com.roncoo.eshop.cache.ha;

import com.roncoo.eshop.cache.ha.http.HttpClientUtils;

/**
 * @Author: cks
 * @Date: Created by 16:15 2018/5/29
 * @Package: com.roncoo.eshop.cache.ha
 * @Description:
 */
public class RequestCollapserTest {
    public static void main(String[] args) {
        HttpClientUtils.sendGetRequest("http://localhost:8084/getProductInfos?productIds=1,1,2,2,3,4");
    }
}
