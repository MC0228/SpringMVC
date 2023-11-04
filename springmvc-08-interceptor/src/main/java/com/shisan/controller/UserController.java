package com.shisan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @Author:shisan
 * @Date:2023/11/4 9:55
 */
@Controller
@RequestMapping("/user")
public class UserController {

    // 直接进入首页
    @RequestMapping("/main")
    public String main() {
        return "main";
    }

    //跳转到登录页面
    @RequestMapping("/jumpLogin")
    public String jumpLogin() {
        return "login";
    }

    @RequestMapping("/login")
    public String login(HttpSession session, String username, String password, Model model) {
        session.setAttribute("username", username);
        model.addAttribute("username", username);
        return "main";
    }

    @RequestMapping("/goOut")
    public String goOut(HttpSession session) {
        session.removeAttribute("username");
        return "redirect:/index.jsp";
    }
}
