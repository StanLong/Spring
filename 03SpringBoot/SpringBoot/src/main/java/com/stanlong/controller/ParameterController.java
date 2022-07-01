package com.stanlong.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @RequestAttribute 获取request域属性
 */
@Controller
public class ParameterController {

    @GetMapping("/goto")
    public String goToPage(HttpServletRequest request){
        request.setAttribute("msg", "转发成功");
        request.setAttribute("code", 200);
        return "forward:/success"; // 转发到 /success 请求
    }

    /**
     * 有两种方式从请求域中取数据
     * 1、使用 @RequestAttribute 注解
     * 2、获取 HttpServletRequest 对象，从 HttpServletRequest 获取
     */
    @ResponseBody
    @GetMapping("/success")
    public Map success(@RequestAttribute("msg") String msg
                        ,@RequestAttribute("code") Integer code
                        ,HttpServletRequest request){
        Object msg1 = request.getAttribute("msg");
        Map<String, Object> map = new HashMap<>();
        map.put("request_msg", msg1); // 从 request 请求域中获取到的值
        map.put("annotation_msg", msg); // 从 @RequestAttribute 注解中获取到请求域中的值
        return map;
    }
}
