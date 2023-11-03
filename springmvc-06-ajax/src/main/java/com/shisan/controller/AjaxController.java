package com.shisan.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author:shisan
 * @Date:2023/11/3 15:51
 */
@RestController
public class AjaxController {

    @RequestMapping("/ajax")
    public String ajax() {
        return "Hello Ajax";
    }
}
