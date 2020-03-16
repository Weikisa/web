package com.wangyang.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;



//一、快速体验缓存
//1.开启基于注解的缓存
//2.标注缓存注解
//3.@Cacheable   @CacheEvict   @CachePut
@SpringBootApplication
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);

        System.out.println("项目已经启动......");
        System.out.println("作者：Weikisa");
    }

}
