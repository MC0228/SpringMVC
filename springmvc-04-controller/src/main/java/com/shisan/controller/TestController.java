package com.shisan.controller;

import com.shisan.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author:shisan
 * @Date:2023/11/1 19:19
 */
@Controller
@RequestMapping("/user")
public class TestController {
    @RequestMapping("/add")
    public String add(User user, Model model) {
        model.addAttribute("user", "add User:" + user.toString());
        return "result";
    }

}
