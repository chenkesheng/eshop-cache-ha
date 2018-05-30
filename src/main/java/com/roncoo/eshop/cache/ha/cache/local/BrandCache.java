package com.roncoo.eshop.cache.ha.cache.local;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: cks
 * @Date: Created by 15:00 2018/5/28
 * @Package: com.roncoo.eshop.cache.ha.cache.local
 * @Description:本地品牌缓存
 */
public class BrandCache {

    private static Map<Long,String> brandMap = new HashMap<>();
    private static Map<Long, Long> productBrandMap = new HashMap<>();

    static {
        brandMap.put(1L, "iphone");
        productBrandMap.put(-1L, 1L);
    }

    public static String getBrandName(Long brandId) {
        return brandMap.get(brandId);
    }

    public static Long getBrandId(Long productId) {
        return productBrandMap.get(productId);
    }
}
