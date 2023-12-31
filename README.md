# Spring MVC

## 1.什么是SpringMVC

**1.概述**

SpringMVC是Spring Framework的一部分，是基于Java是西安的MVC轻量级Web框架

官网文档：https://docs.spring.io/spring/docs/5.2.0.RELEASE/spring-framework-reference/web.html#spring-web

- 优点：

    - 轻量级，简单易学
    - 高效，基于请求响应的MVC框架
    - 与Spring兼容性好，无缝结合
    - 约定大于配置
    - 功能强大： RESTFul、数据验证、格式化、本地化、主体等。
    - 简洁灵活

**用的人多**

## 2.HelloSpring 入门（配置版）

**web.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">
    <!--1.注册DispatcherServlet-->
    <servlet>
        <servlet-name>springmvc</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <!--关联一个springmvc的配置文件:【servlet-name】-servlet.xml-->
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:springmvc-servlet.xml</param-value>
        </init-param>
        <!--启动级别-1-->
        <load-on-startup>1</load-on-startup>
    </servlet>
    <!--/ 匹配所有的请求；（不包括.jsp）-->
    <!--/* 匹配所有的请求；（包括.jsp）-->
    <servlet-mapping>
        <servlet-name>springmvc</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
</web-app>

```

**springmvc-servlet.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!--添加映射器-->
    <bean class="org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping"/>
    <!--添加适配器-->
    <bean class="org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter"/>

    <!--添加视图解析器-->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/jsp/"/>
        <property name="suffix" value=".jsp"/>
    </bean>

    <!--Handler-->
    <bean id="/hello" class="com.shiasan.controller.HelloController"/>

</beans>
```

**HelloController**

```java
package com.shiasan.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//注意：这里我们先导入Controller接口
public class HelloController implements Controller {
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //ModelAndView 模型和视图
        ModelAndView mv = new ModelAndView();
        //封装对象，放在ModelAndView中。Model
        mv.addObject("msg", "HelloSpringMVC!");
        //封装要跳转的视图，放在ModelAndView中
        mv.setViewName("hello"); //: /WEB-INF/jsp/hello.jsp
        return mv;
    }

}

```

**运行Tomcat**
![img.png](img.png)

**遇到的问题**

![img_1.png](img_1.png)

在WEB-INF下添加一个lib的文件夹，将需要用的jar包全部放进去

## 3.使用注解入门SpringMCV

**web.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">

    <!--1.注册DispatcherServlet-->
    <servlet>
        <servlet-name>springmvc</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <!--关联一个springmvc的配置文件:【servlet-name】-servlet.xml-->
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:springmvc-servlet.xml</param-value>
        </init-param>
        <!--启动级别-1-->
        <load-on-startup>1</load-on-startup>
    </servlet>
    <!--/ 匹配所有的请求；（不包括.jsp）-->
    <!--/* 匹配所有的请求；（包括.jsp）-->
    <servlet-mapping>
        <servlet-name>springmvc</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
</web-app>

```

**springmvc-config.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/mvc https://www.springframework.org/schema/mvc/spring-mvc.xsd">

    <!--自动扫面包，指定包下的注解生效，有IOC统一管理-->
    <context:component-scan base-package="com.shisan.controller"/>

    <!--
    让springmvc不处理静态资源
        <bean id="/hello" class="com.shiasan.controller.HelloController"/>
    -->
    <mvc:default-servlet-handler/>
    <!--
        支持mvc注解驱动
        在spring种一百采用@RequsetMapping注解来完成映射关系
        必须向上下文中注册DefaultAnnotationHandlerMapping
        这个两个实例分别在类级别和方法级别处理
        而annotation-driven配置帮助我们完成两个实力的注入
        相当于
        添加映射器
    <bean class="org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping"/>
        添加适配器
    <bean class="org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter"/>

    -->
    <mvc:annotation-driven/>

    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/jsp/"/>
        <property name="suffix" value=".jsp"/>
    </bean>
</beans>
```

**Controller**

```java
package com.shisan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author:shisan
 * @Date:2023/11/1 15:24
 */
@Controller
public class HelloController {
    @RequestMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("msg", "Hello SpringMVC!");
        return "hello";
    }
}
```

**总结**

实现步骤其实非常的简单：

- 新建一个web项目
- 导入相关jar包
- 编写web.xml , 注册DispatcherServlet
- 编写springmvc配置文件
- 接下来就是去创建对应的控制类 , controller
- 最后完善前端视图和controller之间的对应
- 测试运行调试.

使用springMVC必须配置的三大件：

- 处理器映射器、处理器适配器、视图解析器

通常，我们只需要手动配置视图解析器，而处理器映射器和处理器适配器只需要开启注解驱动即可，而省去了大段的xml配置、

## 4.Controller和RESTFul

**解决乱码问题**

- 方法一：

自定义过滤器类

```java
package com.shisan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author:shisan
 * @Date:2023/11/1 20:00
 */
@Controller
@RequestMapping("/test")
public class EncodingController {

    @PostMapping("/t1")
    public String test1(String name, Model model) {
        System.out.println(name);
        model.addAttribute("msg", name);
        return "result";
    }
}

```

在web.xml中添加配置

```xml

<filter>
    <filter-name>encoding</filter-name>
    <filter-class>com.shisan.filter.EncodingFilter</filter-class>
</filter>
<filter-mapping>
<filter-name>encoding</filter-name>
<url-pattern>/*</url-pattern>
</filter-mapping>
```

- 方法二：使用框架中配置字符集编码的过滤器

```xml

<filter>
    <filter-name>encoding</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <init-param>
        <param-name>encoding</param-name>
        <param-value>utf-8</param-value>
    </init-param>
</filter>
<filter-mapping>
<filter-name>encoding</filter-name>
<url-pattern>/*</url-pattern>
</filter-mapping>
```

## 5.JSON

**5.1什么是JSON？**

- JSON是一种轻量型的数据交换格式，目前使用特别广泛
- 采用完全独立于编程语言的文本格式来存储和表达数据
- 简洁和情绪的层次结构使得JSON成为理想的数据交换语言
- 便于人阅读和编写，同时便于机器解析和生成，有效的提高网络传输效率

在 JavaScript 语言中，一切都是对象。因此，任何JavaScript 支持的类型都可以通过 JSON 来表示，例如字符串、数字、对象、数组等。看看他的要求和语法格式：

- 对象表示为键值对，数据由逗号分隔
- 花括号保存对象
- 方括号保存数组

**JSON 键值对**是用来保存 JavaScript 对象的一种方式，和 JavaScript 对象的写法也大同小异，键/值对组合中的键名写在前面并用双引号 “” 包裹，使用冒号 : 分隔，然后紧接着值：

    {"name": "QinJiang"}
    {"age": "3"}
    {"sex": "男"}

**5.2Controller 返回JSON数据**

- Jackson的依赖导入

```xml

<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.10.4</version>
</dependency>
```

- web.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">

    <servlet>
        <servlet-name>dispatcherServlet</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:springmvc-config.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>dispatcherServlet</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>

    <!--<filter>
        <filter-name>encoding</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>utf-8</param-value>
        </init-param>

    </filter>
    <filter-mapping>
        <filter-name>encoding</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>-->
    <filter>
        <filter-name>encoding</filter-name>
        <filter-class>com.shisan.filter.EncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>utf-8</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>encoding</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
</web-app>
```

- springmvc-config.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:mvc="http://www.springframework.org/schema/mvc"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/mvc https://www.springframework.org/schema/mvc/spring-mvc.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">

    <context:component-scan base-package="com.shisan.controller"/>
    <mvc:annotation-driven>
        <mvc:message-converters register-defaults="true">
            <bean class="org.springframework.http.converter.StringHttpMessageConverter">
                <constructor-arg value="UTF-8"/>
            </bean>
            <bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter">
                <property name="objectMapper">
                    <bean class="org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean">
                        <property name="failOnEmptyBeans" value="false"/>
                    </bean>
                </property>
            </bean>
        </mvc:message-converters>
    </mvc:annotation-driven>

    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/jsp/"/>
        <property name="suffix" value=".jsp"/>
    </bean>
</beans>
```

- User.class

```java
package com.shisan.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:shisan
 * @Date:2023/11/3 7:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String name;
    private Integer age;
    private String sex;
}
```

- 需要一个@ResponseBody 一个是ObjectMapper对象

- UserController

```java

@Controller
public class UserController {
    @RequestMapping("/json1")
    @ResponseBody
    public String json1() throws JsonProcessingException {
        //创建一个jackson的对象映射器，用来解析数据
        ObjectMapper mapper = new ObjectMapper();
        //创建一个对象
        User user = new User("徐十三", 3, "男");
        //将我们的对象解析成为json格式
        String str = mapper.writeValueAsString(user);
        //由于@ResponseBody注解，这里会将str转成json格式返回；十分方便
        return str;
    }
}
```

结果出现乱码了

- 设置一下ResponseBody的编码

  //produces:指定响应体返回类型和编码 @RequestMapping(value = "/json1",produces = "application/json;charset=utf-8")

**5.3 全局解决乱码问题**

在springmvc-config.xml中添加一段消息StringHttpMessageConverter转换配置！

```xml

<mvc:annotation-driven>
    <mvc:message-converters register-defaults="true">
        <bean class="org.springframework.http.converter.StringHttpMessageConverter">
            <constructor-arg value="UTF-8"/>
        </bean>
        <bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter">
            <property name="objectMapper">
                <bean class="org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean">
                    <property name="failOnEmptyBeans" value="false"/>
                </bean>
            </property>
        </bean>
    </mvc:message-converters>
</mvc:annotation-driven>
```

- 使用@RestController 就不行在添加@ResponseBody

```java
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
}
```

**5.4使用JSON抽离出来JSONUtils工具类**

```java
package com.shisan.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.text.SimpleDateFormat;

/**
 * @Author:shisan
 * @Date:2023/11/3 11:15
 */
public class JsonUtils {

    public static String getJson(Object object) {
        return getJson(object, "yyyy-MM-dd");
    }

    public static String getJson(Object object, String dataFormat) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dataFormat);
        mapper.setDateFormat(simpleDateFormat);
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
```

- Controller

```java
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
}
```

**5.5使用FastJson**

- 引入依赖jar

```xml

<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.2.76</version>
</dependency>
```

- FastJson主要类：


- 【JSONObject 代表 json 对象 】

    - JSONObject实现了Map接口, 猜想 JSONObject底层操作是由Map实现的。
    - JSONObject对应json对象，通过各种形式的get()方法可以获取json对象中的数据，也可利用诸如size()，isEmpty()
      等方法获取”键：值”对的个数和判断是否为空。其本质是通过实现Map接口并调用接口中的方法完成的。

- 【JSONArray 代表 json 对象数组】

    - 内部是有List接口中的方法来完成操作的。

- 【JSON 代表 JSONObject和JSONArray的转化】

    - JSON类源码分析与使用
    - 仔细观察这些方法，主要是实现json对象，json对象数组，javabean对象，json字符串之间的相互转化。


- 测试类

```java
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

    // 使用fastJson
    @RequestMapping("/json3")
    public String json3() throws JsonProcessingException {
        User user = new User("徐十三", 22, "男");
        return JSON.toJSONString(user);
    }
}
```

## 6.拦截器

**6.1概述**
Springmvc的拦截器拦截雷士Servlet开发中的过滤器Filter，用于对处理器进行预处理和后处理 开发者可以自己定义一些拦截器来实现特定的功能

_**注意**_:拦截器是AOP思想的具体应用

_**过滤器**_

- servlet规范中的一部分，任何java web工程都可以使用
- 在url-pattern中配置了/*之后，可以对所有要访问的资源进行拦截

_**拦截器**_

- 拦截器是SpringMVC框架自己的，只有使用了SpringMVC框架的工程才能使用
- 拦截器只会拦截访问的控制器方法， 如果访问的是jsp/html/css/image/js是不会进行拦截的

**6.2自定义拦截器**

- MyInterceptor

```java
package com.shisan.config;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author:shisan
 * @Date:2023/11/3 19:32
 */
public class MyInterceptor implements HandlerInterceptor {

    /**
     * return false 不执行下一个拦截器
     * return true 执行下一个拦截器
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("=======拦截前=========");
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    // 拦截日志
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("=======拦截后==========");
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    // 拦截日志
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("=======清理=========");
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}

```

- springmvc的配置文件中配置拦截器

```xml
<!--拦截器-->
<mvc:interceptors>
    <mvc:interceptor>
        <mvc:mapping path="/**"/>
        <bean class="com.shisan.config.MyInterceptor"/>
    </mvc:interceptor>
</mvc:interceptors>
```

- InterceptorController

```java
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
```

测试结果：
![img_2.png](img_2.png)

**6.3拦截器实现登录验证**

- 思路

    - 有一个UserController的访问页面
    - 登录页面有一个提交表单的动作。需要在controller中国进行处理。判断用户密码是否正确。正确了向sessio中写入用户信息。跳转登陆成功页面。
    - 拦截用户的请求，判断用户是否登录。如果用户登录，放行；如果用户未登录，跳转到登录页面

- index.jsp

```html
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>十三的测试页面</title>
</head>
<body>
<h1>十三的网站</h1>
<a href="${pageContext.request.contextPath}/user/jumpLogin">登录页面</a>
<a href="${pageContext.request.contextPath}/user/main">首页</a>
</body>
</html>
```

- login.jsp

```html
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>
</head>
<body>
<h1>登陆页面</h1>
<form action="${pageContext.request.contextPath}/user/login" method="post">
    用户名：<input type="text" name="username"><br>
    密码：<input type="password" name="password"><br>
    <input type="submit" value="提交">
</form>
</body>
</html>

```

- main.jsp

```html
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>首页</title>
</head>
<body>
<h1>欢迎的登录:亲爱的${username}</h1>
<p><a href="${pageContext.request.contextPath}/user/goOut">退出登录</a></p>
</body>
</html>
```

- UserController

```java
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
```

- LoginInterceptor

```java
package com.shisan.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Author:shisan
 * @Date:2023/11/4 10:10
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        if (session.getAttribute("username") != null) {
            return true;
        }
        if (request.getRequestURI().contains("login")) {
            return true;
        }
        request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
```

- 在springmvc的配置文件中注册拦截器

```xml

<mvc:interceptors>
    <mvc:interceptor>
        <mvc:mapping path="/user/**/"/>
        <bean class="com.shisan.config.LoginInterceptor"/>
    </mvc:interceptor>
</mvc:interceptors>
```

## 7.文件上传和下载

**7.1文件上传**

- 导入maven依赖

```xml

<dependencies>
    <dependency>
        <groupId>commons-fileupload</groupId>
        <artifactId>commons-fileupload</artifactId>
        <version>1.4</version>
    </dependency>
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>javax.servlet-api</artifactId>
        <version>4.0.1</version>
    </dependency>
</dependencies>
```

- 配置multipartyResolve

**【注意！！！这个bean的id必须为：multipartResolver ， 否则上传文件会报400的错误！在这里栽过坑,教训！】**

```xml
<!--文件上传配置-->
<bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
    <!-- 请求的编码格式，必须和jSP的pageEncoding属性一致，以便正确读取表单的内容，默认为ISO-8859-1 -->
    <property name="defaultEncoding" value="utf-8"/>
    <!-- 上传文件大小上限，单位为字节（10485760=10M） -->
    <property name="maxUploadSize" value="10485760"/>
    <property name="maxInMemorySize" value="40960"/>
</bean>
```

- upload.jsp

```html
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>文件上传</title>
    <script type="text/javascript">
        function check() {
            let name = document.getElementById("name").value;
            let file = document.getElementById("file").value;
            if (name == "") {
                alert("请填写上传为");
                return false;
            }
            if (file.length == 0 || file == "") {
                alert("请选择上传的文件")
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
<form action="${pageContext.request.contextPath}/file/upload"
      method="post" enctype="multipart/form-data" onsubmit="return check()">
    上传人：<input id="name" type="text" name="name"><br>
    <%--多文件上传 multiple="multiple--%>
    请选择文件: <input id="file" type="file" name="files" multiple="multiple"><br>
    <input type="submit" value="上传">
</form>
</body>
</html>
```

- FileController

```java
package com.shisan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

/**
 * @Author:shisan
 * @Date:2023/11/4 14:53
 */
@Controller
@RequestMapping("/file")
public class FileController {
    @RequestMapping("/goUpload")
    public String upload() {
        return "upload";
    }

    @RequestMapping("/upload")
    public String upload(String name, List<MultipartFile> files, HttpServletRequest request) {
        // 判断上传文件是否存在
        if (!files.isEmpty() && files.size() > 0) {
            // 循环输出上传的文件
            for (MultipartFile file : files) {
                String originalFilename = file.getOriginalFilename();
                String dirPath = request.getServletContext().getRealPath("/upload/");
                File filePath = new File(dirPath);

                if (!filePath.exists()) {
                    filePath.mkdirs();
                }

                // 使用UUID重新命名上传的文件名称（上传人_原始文件名名称）
                String newFileName = name + "_" + UUID.randomUUID() + "_" + originalFilename;
                // 使用MultipartFile完成文件上传到指定位置
                try {
                    file.transferTo(new File(dirPath + newFileName));
                    System.out.println(dirPath + newFileName);
                } catch (IOException e) {
                    e.printStackTrace();
                    return "error";
                }
            }
            return "success";
        }
        return "error";

    }
}
```

**7.2文件下载**

- 文件下载步骤：

    - 1.设置response响应头
    - 2.读取文件-InputStream
    - 3.写出文件-OutputStream
    - 4.执行操作
    - 5.关闭流

- FileController

```java
package com.shisan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

/**
 * @Author:shisan
 * @Date:2023/11/4 14:53
 */
@Controller
@RequestMapping("/file")
public class FileController {
    @RequestMapping("/goDownload")
    public String download() {
        return "download";
    }

    @RequestMapping("/download")
    public String downloads(HttpServletResponse response, HttpServletRequest request) throws Exception {
        //要下载的图片地址
        String path = request.getServletContext().getRealPath("/upload");

        String fileName = "01.jpg";
        //1、设置response 响应头
        response.reset(); //设置页面不缓存,清空buffer
        response.setCharacterEncoding("UTF-8"); //字符编码
        response.setContentType("multipart/form-data"); //二进制传输数据
        //设置响应头
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
        File file = new File(path, fileName);
        //2、 读取文件--输入流
        InputStream input = new FileInputStream(file);
        //3、 写出文件--输出流
        OutputStream out = response.getOutputStream();
        byte[] buff = new byte[1024];
        int index = 0;
        //4、执行 写出操作
        while ((index = input.read(buff)) != -1) {
            out.write(buff, 0, index);
            out.flush();
        }
        out.close();
        input.close();
        return "success";
    }

}
```

- download.jsp

```html
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>文件的下载</title>
</head>
<body>
<h1><a href="/upload/01.jpg">点击下载</a>
</h1>
</body>
</html>
```



