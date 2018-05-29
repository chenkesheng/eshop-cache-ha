package com.roncoo.eshop.cache.ha.hystrix.command;

import com.netflix.hystrix.*;
import com.netflix.hystrix.HystrixCommandProperties.ExecutionIsolationStrategy;
import com.roncoo.eshop.cache.ha.cache.local.LocalCache;

/**
 * @Author: cks
 * @Date: Created by 13:47 2018/5/25
 * @Package: com.roncoo.eshop.cache.ha.hystrix.command
 * @Description:信号量资源隔离
 */
public class GetCityNameCommand extends HystrixCommand<String> {

    private Long cityId;

    /**
     * 信号量资源隔离和限流
     * @param cityId
     */
    public GetCityNameCommand(Long cityId) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ProductService"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("GetCityNameCommand"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("GetProductPool"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        //SEMAPHORE代表信号量 THREAD代表线程池
                        .withExecutionIsolationStrategy(ExecutionIsolationStrategy.SEMAPHORE)));
        this.cityId = cityId;
    }

    @Override
    protected String run() {
        return LocalCache.getCityName(cityId);
    }
}
