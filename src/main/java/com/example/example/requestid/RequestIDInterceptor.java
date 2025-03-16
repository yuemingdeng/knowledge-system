package com.example.example.requestid;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 以Spring Boot 为例，可以在拦截器中设置 requestID：
 */
@Component
public class RequestIDInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        RequestIDContext.setRequestID(); // 设置 requestID
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        RequestIDContext.clearRequestID(); // 清理 requestID
    }
}