package com.stanlong.bean;

import lombok.*;

@Getter
@Setter
@ToString
public class User {
    private String name;
    private int age;

    // 使用@Impor导入User组件时，组件中必须要有无参构造方法
    public User(){

    }

    private Pet pet; // 组件依赖
    public User(String name, int age){
        this.name = name;
        this.age = age;
    }
}
