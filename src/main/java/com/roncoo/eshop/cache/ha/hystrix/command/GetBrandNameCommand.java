package com.roncoo.eshop.cache.ha.hystrix.command;

import com.netflix.hystrix.*;
import com.roncoo.eshop.cache.ha.cache.local.BrandCache;

/**
 * @Author: cks
 * @Date: Created by 14:49 2018/5/28
 * @Package: com.roncoo.eshop.cache.ha.hystrix.command
 * @Description:
 */
public class GetBrandNameCommand extends HystrixCommand<String> {

    private Long brandId;

    public GetBrandNameCommand(Long brandId) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("BrandInfoService"))
        .andCommandKey(HystrixCommandKey.Factory.asKey("GetBrandNameCommand"))
        .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("GetBrandInfoPool"))
        .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
        .withCoreSize(15)
        .withQueueSizeRejectionThreshold(10))
        .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
        .withFallbackIsolationSemaphoreMaxConcurrentRequests(15)));

        this.brandId = brandId;
    }

    @Override
    protected String run() throws Exception {
        throw new Exception();
    }

    @Override
    protected String getFallback() {
        System.out.println("从本地缓存获取过期的品牌数据，brandId=" + brandId);
        return BrandCache.getBrandName(brandId);
    }
}
