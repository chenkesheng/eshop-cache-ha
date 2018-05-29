package com.roncoo.eshop.cache.ha;

import com.roncoo.eshop.cache.ha.http.HttpClientUtils;

/**
 * @Author: cks
 * @Date: Created by 16:47 2018/5/28
 * @Package: com.roncoo.eshop.cache.ha
 * @Description:
 */
public class RejectTest {

    public static void main(String[] args) throws Exception {
        for(int i = 0; i < 25; i++) {
            new TestThread(i).start();
        }
    }

    private static class TestThread extends Thread {

        private int index;

        public TestThread(int index) {
            this.index = index;
        }

        @Override
        public void run() {
            String response = HttpClientUtils.sendGetRequest("http://localhost:8084/getProductInfo?productId=-2");
            System.out.println("第" + (index + 1) + "次请求，结果为：" + response);
        }

    }
}
