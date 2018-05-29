package com.roncoo.eshop.cache.ha.hystrix.command;

import com.alibaba.fastjson.JSONObject;
import com.netflix.hystrix.*;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;
import com.roncoo.eshop.cache.ha.http.HttpClientUtils;
import com.roncoo.eshop.cache.ha.model.ProductInfo;

/**
 * @Author: cks
 * @Date: Created by 16:52 2018/5/24
 * @Package: com.roncoo.eshop.cache.ha.hystrix.command
 * @Description:获取商品信息
 */
public class GetProductInfoCommand extends HystrixCommand<ProductInfo> {

    private static final HystrixCommandKey KEY = HystrixCommandKey.Factory.asKey("GetProductInfoCommand");

    private Long productId;

    public GetProductInfoCommand(Long productId) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ProductInfoService"))
                .andCommandKey(KEY)
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("GetProductInfoPool"))
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                        .withCoreSize(10)//设置线程池大小
                        .withMaxQueueSize(12)//设置的是等待缓冲队列的大小
                        .withQueueSizeRejectionThreshold(15))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withCircuitBreakerRequestVolumeThreshold(30)
                        .withCircuitBreakerErrorThresholdPercentage(40)
                        .withCircuitBreakerSleepWindowInMilliseconds(3000)
                        .withExecutionTimeoutInMilliseconds(200)
                        .withFallbackIsolationSemaphoreMaxConcurrentRequests(30))
        );
        this.productId = productId;
    }

    @Override
    protected ProductInfo run() throws Exception {
        System.out.println("调用接口，查询商品数据，productId=" + productId);

        if (productId.equals(-1L)) {
            throw new Exception();
        }

        if (productId.equals(-2L)){
            Thread.sleep(3000);
        }

        if (productId.equals(-3L)){
//            Thread.sleep(100);
        }

        String url = "http://127.0.0.1:8085/getProductInfo?productId=" + productId;
        String response = HttpClientUtils.sendGetRequest(url);
        ProductInfo productInfo = JSONObject.parseObject(response, ProductInfo.class);
        return productInfo;
    }

//    @Override
//    protected String getCacheKey() {
//        return "product_info_" + productId;
//    }
//
//    public static void flushCache(Long productId){
//        HystrixRequestCache.getInstance(KEY, HystrixConcurrencyStrategyDefault.getInstance()).clear("product_info_" + productId);
//    }


    @Override
    protected ProductInfo getFallback() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setName("降级商品");
        return productInfo;
    }
}
