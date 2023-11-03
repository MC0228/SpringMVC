package com.shisan.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.shisan.pojo.User;
import com.shisan.utils.JsonUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author:shisan
 * @Date:2023/11/3 7:43
 */
//@Controller 返回视图解析器
@RestController // 返回json字符串 等价于 controller + ResponseBody
public class UserController {


    //produces:指定响应体返回类型和编码
    @RequestMapping("/user")
//    @ResponseBody
    public String test() throws JsonProcessingException {
        User user = new User("徐十三", 22, "男");
        User user2 = new User("徐十三2", 22, "男");
        User user3 = new User("徐十三3", 22, "男");
        User user4 = new User("徐十三4", 22, "男");
        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        return JsonUtils.getJson(users);
    }

    //使用纯Java方式返回时间戳
    @RequestMapping("/json")
    public String json() throws JsonProcessingException {
        return JsonUtils.getJson(new Date());
    }

    // 使用jackson
    @RequestMapping("/json2")
    public String json2() throws JsonProcessingException {
        Date date = new Date();
        return JsonUtils.getJson(date, "yyyy-MM-dd HH:mm:ss");
    }

    // 使用fastJson
    @RequestMapping("/json3")
    public String json3() throws JsonProcessingException {
        User user = new User("徐十三", 22, "男");
        return JSON.toJSONString(user);
    }
}
