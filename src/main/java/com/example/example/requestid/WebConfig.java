package com.example.example.requestid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 在Spring Boot 中注册拦截器
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private RequestIDInterceptor requestIDInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestIDInterceptor);
    }
}
