package com.stanlong;

import com.stanlong.bean.Pet;
import com.stanlong.bean.User;
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

        // 从容器中获取组件
        // 如果@Configuration(proxyBeanMethods = true)，单例，SpringBoot 不会重复创建对象。如果是false，那就是多例
        User user01 = run.getBean("user01", User.class);
        User user02 = run.getBean("user01", User.class);
        System.out.println(user01 == user02); // true, 注册的组件默认是单实例的

        // 单例模式下，组件依赖验证
        User user03 = run.getBean("user01", User.class);
        Pet tom = run.getBean("tom", Pet.class);
        System.out.println("用户的宠物："+(user03.getPet() == tom)); // true

        System.out.println("==========================================");

        // 获取容器中的所有User组件
        String[] beanNamesForType = run.getBeanNamesForType(User.class);
        for(String beanName : beanNamesForType){
            System.out.println(beanName);
        }
        // 控制台打印
        // com.stanlong.bean.User 这个是 @Import 组件导入的
        // user01 这个是 @Bean 注解添加进来的

    }
}
