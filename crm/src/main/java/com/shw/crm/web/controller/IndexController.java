package com.shw.crm.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping("/")
    public String index(){
        //请求转发
        //System.out.println("请求转发1");
        return "index";
    }
}
