package com.stanlong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication()
public class AdminApplication {
    public static void main(String[] args) {
        // 返回IOC 容器
        ConfigurableApplicationContext run = SpringApplication.run(AdminApplication.class, args);

        // 查看容器里面的组件
        // String[] names = run.getBeanDefinitionNames();
        // for(String name : names){
        //     System.out.println(name);
        // }

        boolean tom = run.containsBean("tom");
        System.out.println("容器中是否包含tom组件: " + tom);

        boolean user01 = run.containsBean("user01");
        System.out.println("容器中是否包含user01组件: " + user01);

    }
}
