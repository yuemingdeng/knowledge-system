package com.example.controller;

import com.example.example.requestid.MultiThreadExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController  //默认类中的方法都以json格式返回
@RequestMapping(value = "/api/test")
public class TestController {

//    @Value("${com.neo.title}")
//    private String name;

    @Autowired
    private MultiThreadExample multiThreadExample;

    @GetMapping
    public void get2() {
        multiThreadExample.test();
    }
}
