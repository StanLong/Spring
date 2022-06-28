# YMAL的用法

YAML 是 "YAML Ain't Markup Language"（YAML 不是一种标记语言）的递归缩写。在开发这种语言时，YAML 的意思其实是："Yet Another Markup Language"（仍是一种标记语言）。 非常适合用来做以数据为中心的配置文件。**注意**：properties 配置文件的优先级比yml的优先级要高

## 基本语法

- key: value；kv之间有空格
- 大小写敏感
- 使用缩进表示层级关系
- 缩进不允许使用tab，只允许空格
- 缩进的空格数不重要，只要相同层级的元素左对齐即可
- '#'表示注释
- 字符串无需加引号，如果要加，单引号会将 转义字符作为字符串输出，双引号则表会表示出转义字符本来的含义

## 数据类型

YAML 支持以下几种数据类型：

```
- 字面量：单个的、不可再分的值
- 对象：键值对的集合，又称为映射（mapping）/ 哈希（hashes） / 字典（dictionary）
- 数组：一组按次序排列的值，又称为序列（sequence） / 列表（list）
```

- 字面量：单个的、不可再分的值。如：date、boolean、string、number、null

  实例如下：

  ```yaml
  boolean: 
      - TRUE  #true,True都可以
      - FALSE  #false，False都可以
  float:
      - 3.14
      - 6.8523015e+5  #可以使用科学计数法
  int:
      - 123
      - 0b1010_0111_0100_1010_1110    #二进制表示
  null:
      nodeName: 'node'
      parent: ~  #使用~表示null
  string:
      - 哈哈
      - 'Hello world'  #可以使用双引号或者单引号包裹特殊字符
      - newline
        newline2    #字符串可以拆成多行，每一行会被转化成一个空格
  date:
      - 2018-02-17    #日期必须使用ISO 8601格式，即yyyy-MM-dd
  datetime: 
      -  2018-02-17T15:02:31+08:00    #时间使用ISO 8601格式，时间和日期之间使用T连接，最后使用+代表时区
  ```

- 对象：键值对的集合。如map、hash、set、object 。

  对象键值对使用冒号结构表示 **key: value**，冒号后面要加一个空格。也可以使用 **key:{key1: value1, key2: value2, ...}**。

  写法如下：

  ```
  #行内写法：  
  k: {k1:v1,k2:v2,k3:v3}
  
  #或
  k: 
    k1: v1
    k2: v2
    k3: v3
  ```

- 数组：一组按次序排列的值，以 `-` 开头的行表示构成一个数组：如 array、list、queue

  ```
  #行内写法：  
  k: [v1,v2,v3]
  
  #或者
  k:
   - v1
   - v2
   - v3
  ```

## 操作实例

1、准备组件

```java
package com.stanlong.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@ConfigurationProperties(prefix = "person")
@Getter
@Setter
@ToString
@Component
public class Person {
    private String userName;
    private Boolean boss;
    private Date birth;
    private Integer age;
    private Pet pet;
    private String[] interests;
    private List<String> animal;
    private Map<String, Object> score;
    private Set<Double> salaries;
    private Map<String, List<Pet>> allPets;
}

```

```java
package com.stanlong.bean;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Pet {
    private String name;
}
```

2、在resource目录下创建配置文件 application.yml，用yaml表示以上对象

```yaml
person:
  userName: zhangsan
  boss: false
  birth: 2019/12/12 20:12:33
  age: 18
  pet: 
    name: tomcat
  interests: [篮球,游泳] # 行内写法
  animal: 
    - jerry
    - mario
  score:
    english: 
      first: 30
      second: 40
      third: 50
    math: [131,140,148]
    chinese: {first: 128,second: 136}
  salaries: [3999, 4999.98, 5999.99]
  allPets:
    sick:
      - {name: tom}
      - {name: jerry}
    health: [{name: mario}]
```

测试

```java
package com.stanlong.controller;

import com.stanlong.bean.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    
    private Person person;
    
    @Autowired
    public void setPerson(Person person) {
        this.person = person;
    }

    @RequestMapping("/person")
    public Person person(){
        return person;
    }
}
```

启动主类

```java
package com.stanlong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}
```

页面访问 http://localhost:8080/person

输入结果

```json
{
	"userName": "zhangsan",
	"boss": false,
	"birth": "2019-12-12T12:12:33.000+00:00",
	"age": 18,
	"pet": {
		"name": "tomcat"
	},
	"interests": ["篮球", "游泳"],
	"animal": ["jerry", "mario"],
	"score": {
		"english": {
			"first": 30,
			"second": 40,
			"third": 50
		},
		"math": {
			"0": 131,
			"1": 140,
			"2": 148
		},
		"chinese": {
			"first": 128,
			"second": 136
		}
	},
	"salaries": [3999.0, 4999.98, 5999.99],
	"allPets": {
		"sick": [{
			"name": "tom"
		}, {
			"name": "jerry"
		}],
		"health": [{
			"name": "mario"
		}]
	}
}
```

# 配置提示

自定义的类和配置文件绑定一般没有提示。若要提示，添加如下依赖：

```xml
<!--配置自定义的类和配置文件(yml, properties)绑定的提示功能-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-configuration-processor</artifactId>
    <optional>true</optional>
</dependency>

<!-- spring-boot-configuration-processor 部署的时候不需要打进包里，需要配置如下插件 -->
<!-- 下面插件作用是工程打包时，不将spring-boot-configuration-processor打进包内，让其只在编码的时候有用 -->
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <configuration>
                <excludes>
                    <exclude>
                        <groupId>org.springframework.boot</groupId>
                        <artifactId>spring-boot-configuration-processor</artifactId>
                    </exclude>
                </excludes>
            </configuration>
        </plugin>
    </plugins>
</build>
```

