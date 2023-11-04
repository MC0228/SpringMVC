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

    // 在登陆完成之后跳转到首页
    @RequestMapping("/login")
    public String login(HttpSession session, String username, String password, Model model) {
        // 把用户信息存入到session中
        session.setAttribute("username", username);
        model.addAttribute("username", username);
        return "main";
    }

    // 退出登陆之后，注销掉
    @RequestMapping("/goOut")
    public String goOut(HttpSession session) {
        // 从session中注销掉username
        session.removeAttribute("username");
        // 然后重定向到index页面
//        return "redirect:/index.jsp";
        return "forward:/index.jsp";

    }


}
