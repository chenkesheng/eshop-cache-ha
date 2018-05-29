package com.roncoo.eshop.cache.ha.hystrix.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * @Author: cks
 * @Date: Created by 14:22 2018/5/28
 * @Package: com.roncoo.eshop.cache.ha.hystrix.command
 * @Description:
 */
public class UpdateProductInfoCommand extends HystrixCommand<Boolean> {

    private Long productId;

    public UpdateProductInfoCommand(Long productId){
        super(HystrixCommandGroupKey.Factory.asKey("UpdateProductInfoGroup"));
        this.productId = productId;
    }

    @Override
    protected Boolean run() throws Exception {
//        GetProductInfoCommand.flushCache(productId);
        return true;
    }
}
