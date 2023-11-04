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
