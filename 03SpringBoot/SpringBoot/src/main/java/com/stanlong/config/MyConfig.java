package com.stanlong.config;

import com.stanlong.bean.Pet;
import com.stanlong.bean.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 1、配置类里面使用@Bean标注在方法上给容器注册组件，默认也是单实例的
 * 2、配置类本身也是组件
 * 3、proxyBeanMethods：代理bean的方法
 *   proxyBeanMethods = true（保证每个@Bean方法被调用多少次返回的组件都是单实例的）（默认）(也被称为 Full 全配置)
 *   proxyBeanMethods = false（每个@Bean方法被调用多少次返回的组件都是新创建的）（多例）（也被称为 Lite 轻量级配置）
 *
 * 4、@Import({User.class, DBHelper.class})
 *   调用组件的无参构造器，创建出指定类型的对象放到容器中。默认组件的名字就是全类名
 */
@Import({User.class})
@Configuration(proxyBeanMethods = false) // 告诉SpringBoot这是一个配置类 == 配置文件
public class MyConfig {

    @Bean //给容器中添加组件。以方法名作为组件的id。返回类型就是组件类型。返回的值，就是组件在容器中的实例
    public User user01(){
        User zhangsan = new User("zhangsan", 18);
        //user组件依赖了Pet组件
        zhangsan.setPet(tomcatPet());
        return zhangsan;
    }

    @Bean("tom") // 如果不想以方法名作为组件id，可以给它一个自定义的名字,比如 tom
    public Pet tomcatPet(){
        return new Pet("tomcat");
    }
}
