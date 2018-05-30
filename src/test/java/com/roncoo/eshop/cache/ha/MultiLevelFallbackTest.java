package com.roncoo.eshop.cache.ha;

import com.roncoo.eshop.cache.ha.hystrix.command.GetProductInfoCommand;

/**
 * @Author: cks
 * @Date: Created by 14:22 2018/5/30
 * @Package: com.roncoo.eshop.cache.ha
 * @Description:
 */
public class MultiLevelFallbackTest {
    public static void main(String[] args) throws Exception {
        GetProductInfoCommand getProductInfoCommand1 = new GetProductInfoCommand(-1L);
        System.out.println(getProductInfoCommand1.execute());
        GetProductInfoCommand getProductInfoCommand2 = new GetProductInfoCommand(-2L);
        System.out.println(getProductInfoCommand2.execute());
    }
}
