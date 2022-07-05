# 整合MyBatis

[MyBatis的GitHub仓库](https://github.com/mybatis)

[MyBatis官方](https://mybatis.org/mybatis-3/zh/index.html)

## 一、配置版

1、引入依赖

```xml
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>2.1.4</version>
</dependency>
```

2、YAML配置

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/spring_jdbctemplent
    username: root
    password: root
    driver-class-name: com.mysql.jdbc.Driver

# 配置mybatis规则
mybatis:
  config-location: classpath:mybatis/mybatis-config.xml  #全局配置文件位置
  mapper-locations: classpath:mybatis/mapper/*.xml  #sql映射文件位置
```

3、mybatis-config.xml配置

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>

    <!-- 由于Spring Boot自动配置缘故，此处不必配置，只用来做做样。-->

</configuration>
```

4、准备组件测试

User表

```sql
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('1', 'wangwu', '789');
INSERT INTO `tb_user` VALUES ('2', 'wangwu', '789');
INSERT INTO `tb_user` VALUES ('3', 'zhangsansan', '32221');
```

实体类

```java
package com.stanlong.bean;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String userName;
    private String password;
}
```

UserMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.stanlong.mapper.UserMapper">

    <select id="getUser" resultType="com.stanlong.bean.User">
        select * from tb_user where id=#{id}
    </select>
</mapper>
```

UserMapper.java

```java
package com.stanlong.mapper;

import com.stanlong.bean.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User getUser(long userId);
}
```

UserService

```java
package com.stanlong.service;

import com.stanlong.bean.User;
import com.stanlong.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public User getUser(Long id){
        return userMapper.getUser(id);
    }
}
```

UserController

```java
package com.stanlong.controller;

import com.stanlong.bean.User;
import com.stanlong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @ResponseBody
    @GetMapping("/user/{id}")
    public User getUser(@PathVariable("id") Long id){

        return userService.getUser(id);
    }
}
```

5、启动主类  访问 `http://localhost:8080/user/1` 页面响应数据如下

```json
{
	"id": 1,
	"userName": "wangwu",
	"password": "789"
}
```

## 二、注解版







