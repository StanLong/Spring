package com.stanlong.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewTestController {
    @GetMapping("/hello")
    public String hello(Model model){
        // model中的数据会被放在请求域中
        model.addAttribute("msg","Hello Thymeleaf");
        return "success";
    }
}
