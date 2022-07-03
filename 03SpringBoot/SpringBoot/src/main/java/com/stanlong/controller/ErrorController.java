package com.stanlong.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {
    @GetMapping("/divide")
    public String divide(){
        int i = 10/0;
        return "division";
    }
}
