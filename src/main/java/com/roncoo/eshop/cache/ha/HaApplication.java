package com.roncoo.eshop.cache.ha;

import com.roncoo.eshop.cache.ha.filter.HystrixRequestContextFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan("com.roncoo.eshop.cache.ha.mapper")
public class HaApplication {

    public static void main(String[] args) {
        SpringApplication.run(HaApplication.class, args);
    }

//    @Bean
//    public FilterRegistrationBean indexFilterRegistration() {
//        FilterRegistrationBean registration = new FilterRegistrationBean(new HystrixRequestContextFilter());
//        registration.addUrlPatterns("/*");
//        return registration;
//    }
}
