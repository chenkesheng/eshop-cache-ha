package com.roncoo.eshop.cache.ha.controller;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixObservableCommand;
import com.roncoo.eshop.cache.ha.http.HttpClientUtils;
import com.roncoo.eshop.cache.ha.hystrix.command.GetBrandNameCommand;
import com.roncoo.eshop.cache.ha.hystrix.command.GetCityNameCommand;
import com.roncoo.eshop.cache.ha.hystrix.command.GetProductInfoCommand;
import com.roncoo.eshop.cache.ha.hystrix.command.GetProductInfosCommand;
import com.roncoo.eshop.cache.ha.model.ProductInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import rx.Observable;
import rx.Observer;

/**
 * @Author: cks
 * @Date: Created by 14:09 2018/5/23
 * @Package: com.roncoo.eshop.cache.ha.controller
 * @Description:
 */
@RestController
public class CacheController {

    @GetMapping("/change/product")
    public String changeProduct(Long productId) {
        // 拿到一个商品id
        // 调用商品服务的接口，获取商品id对应的商品的最新数据
        // 用HttpClient去调用商品服务的http接口
        String url = "http://127.0.0.1:8085/getProductInfo?productId=" + productId;
        String response = HttpClientUtils.sendGetRequest(url);
        System.out.println(response);
        return "success";
    }

    /**
     * nginx开始，各级缓存都失效了，nginx发送很多的请求直接到缓存服务要求拉取最原始的数据
     *
     * @param productId
     * @return
     */
    @GetMapping("/getProductInfo")
    public String getProductInfo(Long productId) {
        // 拿到一个商品id
        // 调用商品服务的接口，获取商品id对应的商品的最新数据
        // 用HttpClient去调用商品服务的http接口
        HystrixCommand<ProductInfo> getProductInfoCommand = new GetProductInfoCommand(productId);
        ProductInfo productInfo = getProductInfoCommand.execute();

        Long cityId = productInfo.getCityId();
//        String cityName = LocalCache.getCityName(cityId);
        GetCityNameCommand getCityNameCommand = new GetCityNameCommand(cityId);
        productInfo.setCityName(getCityNameCommand.execute());

        Long brandId = productInfo.getBrandId();
        GetBrandNameCommand getBrandNameCommand = new GetBrandNameCommand(brandId);
        String brandName = getBrandNameCommand.execute();
        productInfo.setBrandName(brandName);
//		Future<ProductInfo> future = getProductInfoCommand.queue();
//		try {
//			Thread.sleep(1000);
//			System.out.println(future.get());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

        System.out.println(productInfo);
        return productInfo.toString();
    }

    /**
     * 一次性批量查询多条商品数据的请求
     */
    @GetMapping("/getProductInfos")
    public String getProductInfos(String productIds) {
//        HystrixObservableCommand<ProductInfo> getProductInfosCommand =
//                new GetProductInfosCommand(productIds.split(","));
//        Observable<ProductInfo> observable = getProductInfosCommand.observe();
//
////		observable = getProductInfosCommand.toObservable(); // 还没有执行
//
//        observable.subscribe(new Observer<ProductInfo>() { // 等到调用subscribe然后才会执行
//
//            public void onCompleted() {
//                System.out.println("获取完了所有的商品数据");
//            }
//
//            public void onError(Throwable e) {
//                e.printStackTrace();
//            }
//
//            public void onNext(ProductInfo productInfo) {
//                //返回一条数据就调用这个一次方法
//                System.out.println(productInfo);
//            }
//
//        });

        for (String productId : productIds.split(",")) {
            GetProductInfoCommand getProductInfoCommand = new GetProductInfoCommand(Long.valueOf(productId));
            ProductInfo productInfo = getProductInfoCommand.execute();
            System.out.println(productInfo);
            System.out.println(getProductInfoCommand.isResponseFromCache());
        }
        return "success";
    }
}
