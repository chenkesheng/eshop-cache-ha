package com.roncoo.eshop.cache.ha.cache.local;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: cks
 * @Date: Created by 13:38 2018/5/25
 * @Package: com.roncoo.eshop.cache.ha.cache.local
 * @Description:
 */
public class  LocalCache {

    private static Map<Long,String> cityMap =  new HashMap<>();

    static {
        cityMap.put(1l, "北京");
    }

    public static String getCityName(Long cityId){
        return cityMap.get(cityId);
    }
}
