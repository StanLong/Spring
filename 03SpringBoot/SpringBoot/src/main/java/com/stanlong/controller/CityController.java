package com.stanlong.controller;

import com.stanlong.bean.City;
import com.stanlong.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CityController {

    @Autowired
    public CityService cityService;

    @ResponseBody
    @GetMapping("/city")
    public City getCityById(@RequestParam("id") Long id){
        return cityService.getCityById(id);
    }

    @ResponseBody
    @PostMapping("/city")
    public City insert(City city){
        cityService.insert(city);
        return city;
    }
}
