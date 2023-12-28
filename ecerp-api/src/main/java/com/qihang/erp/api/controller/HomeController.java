package com.qihang.erp.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @Autowired
    private MySpiderController spiderController;

    @GetMapping("/test/22")
    public String home(){
        spiderController.startSpider();
        return "hello world";
    }
}
