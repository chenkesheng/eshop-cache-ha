package com.roncoo.eshop.cache.ha.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: cks
 * @Date: Created by 13:42 2018/5/23
 * @Package: com.roncoo.eshop.cache.ha.controller
 * @Description:
 */
@RestController("/")
public class HelloController {

    @GetMapping("hello")
    public String hello(String name) {
        return "hello," + name;
    }
}
