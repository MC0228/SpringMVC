package com.shisan.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author:shisan
 * @Date:2023/11/3 18:50
 */
@RestController
public class InterceptorController {
    @RequestMapping("/interceptor")
    public String interceptor() {
        System.out.println("初始化了拦截器");
        return "Interceptor 拦截器";
    }
}
