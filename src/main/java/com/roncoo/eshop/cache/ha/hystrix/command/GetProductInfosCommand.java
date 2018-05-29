package com.roncoo.eshop.cache.ha.hystrix.command;

import com.alibaba.fastjson.JSONObject;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import com.roncoo.eshop.cache.ha.http.HttpClientUtils;
import com.roncoo.eshop.cache.ha.model.ProductInfo;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * @Author: cks
 * @Date: Created by 17:07 2018/5/24
 * @Package: com.roncoo.eshop.cache.ha.hystrix.command
 * @Description:
 */
public class GetProductInfosCommand extends HystrixObservableCommand<ProductInfo> {

    private String[] productIds;

    public GetProductInfosCommand(String[] productIds) {
        super(HystrixCommandGroupKey.Factory.asKey("GetProductInfoGroup"));
        this.productIds = productIds;
    }

    @Override
    protected Observable<ProductInfo> construct() {
        return Observable.create((Observable.OnSubscribe<ProductInfo>) observer -> {
            try {
                for (String productId : productIds) {
                    String url = "http://127.0.0.1:8085/getProductInfo?productId=" + productId;
                    String response = HttpClientUtils.sendGetRequest(url);
                    ProductInfo productInfo = JSONObject.parseObject(response, ProductInfo.class);
                    observer.onNext(productInfo);
                }
                observer.onCompleted();
            } catch (Exception e) {
                observer.onError(e);
            }
        }).subscribeOn(Schedulers.io());
    }
}
