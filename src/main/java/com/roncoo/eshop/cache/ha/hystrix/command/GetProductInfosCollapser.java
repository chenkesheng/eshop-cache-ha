package com.roncoo.eshop.cache.ha.hystrix.command;

import com.alibaba.fastjson.JSONArray;
import com.netflix.hystrix.*;
import com.roncoo.eshop.cache.ha.http.HttpClientUtils;
import com.roncoo.eshop.cache.ha.model.ProductInfo;

import java.util.Collection;
import java.util.List;

/**
 * @Author: cks
 * @Date: Created by 15:56 2018/5/29
 * @Package: com.roncoo.eshop.cache.ha.hystrix.command
 * @Description:
 */
public class GetProductInfosCollapser extends HystrixCollapser<List<ProductInfo>, ProductInfo, Long> {

    private Long productId;

    public GetProductInfosCollapser(Long productId) {
        super(Setter.withCollapserKey(HystrixCollapserKey.Factory.asKey("GetProductInfosCollapser"))
                .andCollapserPropertiesDefaults(HystrixCollapserProperties.Setter().withTimerDelayInMilliseconds(100)));
        this.productId = productId;
    }

    @Override
    public Long getRequestArgument() {
        return productId;
    }

    @Override
    protected HystrixCommand<List<ProductInfo>> createCommand(Collection<CollapsedRequest<ProductInfo, Long>> collection) {
        StringBuilder paramsBuilder = new StringBuilder();
        for (CollapsedRequest<ProductInfo, Long> request : collection) {
            paramsBuilder.append(request.getArgument()).append(",");
        }
        String params = paramsBuilder.toString();
        params = params.substring(0, params.length() - 1);

        System.out.println("createCommand方法执行，params=" + params);

        return new BatchCommand(collection);
    }

    @Override
    protected void mapResponseToRequests(List<ProductInfo> productInfos, Collection<CollapsedRequest<ProductInfo, Long>> collection) {
        int count = 0;
        for (CollapsedRequest<ProductInfo, Long> request : collection) {
            request.setResponse(productInfos.get(count++));
        }
    }

    @Override
    protected String getCacheKey() {
        return "product_info_" + productId;
    }

    private static final class BatchCommand extends HystrixCommand<List<ProductInfo>> {

        public final Collection<CollapsedRequest<ProductInfo, Long>> requests;

        public BatchCommand(Collection<CollapsedRequest<ProductInfo, Long>> requests) {
            super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ProductInfoService"))
                    .andCommandKey(HystrixCommandKey.Factory.asKey("GetProductInfosCollapserBatchCommand")));
            this.requests = requests;
        }

        @Override
        protected List<ProductInfo> run() {
            // 将一个批次内的商品id给拼接在了一起
            StringBuilder paramsBuilder = new StringBuilder();
            for (CollapsedRequest<ProductInfo, Long> request : requests) {
                paramsBuilder.append(request.getArgument()).append(",");
            }
            String params = paramsBuilder.toString();
            params = params.substring(0, params.length() - 1);

            // 在这里，我们可以将多个商品id合并在一个batch内，直接发送一次网络请求，获取到所有的结果

            String url = "http://localhost:8085/getProductInfos?productIds=" + params;
            String response = HttpClientUtils.sendGetRequest(url);

            List<ProductInfo> productInfos = JSONArray.parseArray(response, ProductInfo.class);
            for (ProductInfo productInfo : productInfos) {
                System.out.println("BatchCommand内部，productInfo=" + productInfo);
            }

            return productInfos;
        }
    }
}
