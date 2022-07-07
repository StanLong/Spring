# 整合MyBatisPlus

[IDEA的MyBatis的插件 - MyBatisX](https://plugins.jetbrains.com/plugin/10119-mybatisx)

[MyBatisPlus官网](https://baomidou.com/)

[MyBatisPlus官方文档](https://baomidou.com/guide/)

[MyBatis-Plus](https://github.com/baomidou/mybatis-plus)（简称 MP）是一个 [MyBatis](http://www.mybatis.org/mybatis-3/)的增强工具，在 MyBatis 的基础上只做增强不做改变，为简化开发、提高效率而生。

- `MybatisPlusAutoConfiguration`配置类，`MybatisPlusProperties`配置项绑定。
- `SqlSessionFactory`自动配置好，底层是容器中默认的数据源。
- `mapperLocations`自动配置好的，有默认值`classpath*:/mapper/**/*.xml`，这表示任意包的类路径下的所有mapper文件夹下任意路径下的所有xml都是sql映射文件。  建议以后sql映射文件放在 mapper下。
- 容器中也自动配置好了`SqlSessionTemplate`。
- `@Mapper` 标注的接口也会被自动扫描，建议直接 `@MapperScan("com.lun.boot.mapper")`批量扫描。
- MyBatisPlus**优点**之一：只需要我们的Mapper继承MyBatisPlus的`BaseMapper` 就可以拥有CRUD能力，减轻开发工作。

## 官网案例

1、创建一张测试表

```sql
CREATE TABLE user
(
    id BIGINT(20) NOT NULL COMMENT '主键ID',
    name VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
    age INT(11) NULL DEFAULT NULL COMMENT '年龄',
    email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
    PRIMARY KEY (id)
);

DELETE FROM user;

INSERT INTO user (id, name, age, email) VALUES
(1, 'Jone', 18, 'test1@baomidou.com'),
(2, 'Jack', 20, 'test2@baomidou.com'),
(3, 'Tom', 28, 'test3@baomidou.com'),
(4, 'Sandy', 21, 'test4@baomidou.com'),
(5, 'Billie', 24, 'test5@baomidou.com');
```

2、添加依赖

```xml
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.4.1</version>
</dependency>
```

3、编写实体类

```java
package com.stanlong.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
@TableName("user") // 默认实体类的类名和数据库的表名保持一致，如果不一致。如果添加 @TableName 注解指定表名
public class User {

    /**
     * 使用mybatisplus所有的属性在数据库中都应该有，如果需要临时属性，需要加上注解 @TableField(exist = false)
     */
    @TableField(exist = false)
    private String userName;
    @TableField(exist = false)
    private String password;

    private Long id;
    private String name;
    private Integer age;
    private String email;
}
```

4、编写接口

```java
package com.stanlong.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stanlong.bean.User;

// 主类中统一扫描 mapper 文件夹，因此对应的类上不需要加mapper注解
public interface UserMapper extends BaseMapper<User> {

}
```

5、修改主类

```java
package com.stanlong;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
@MapperScan("com.stanlong.mapper") // 在启动类中添加 @MapperScan 注解，扫描 Mapper 文件夹：
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}
```

6、编写测试类

```java
package com.stanlong;

import com.stanlong.bean.User;
import com.stanlong.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class AdminApplicationTests {

    @Autowired
    private UserMapper userMapper;


    @Test
    public void testUserMapper(){
        User user = userMapper.selectById(1L);
        System.out.println(user);
    }
}
```

7、启动主类，运行测试类。结果如下

```
User(userName=null, password=null, id=1, name=Jone, age=18, email=test1@baomidou.com)
```



