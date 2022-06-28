package com.stanlong.controller;

import com.stanlong.bean.Car;
import com.stanlong.bean.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {


    private Car car;
    private Person person;


    @Autowired
    public void setCar(Car car) {
        this.car = car;
    }

    @Autowired
    public void setPerson(Person person) {
        this.person = person;
    }

    @RequestMapping("/person")
    public Person person(){
        return person;
    }

    @RequestMapping("/car")
    public Car car(){
        return car;
    }

    @RequestMapping("/hello")
    public String hello(){
        return "Hello Spring Boot2";
    }
}