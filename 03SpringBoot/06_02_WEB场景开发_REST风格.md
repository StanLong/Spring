# REST风格

Rest风格支持（使用**HTTP**请求方式动词来表示对资源的操作）

- 以前：
  - /getUser 获取用户
  - /deleteUser 删除用户
  - /editUser 修改用户
  - /saveUser保存用户
- 现在： /user 
  - GET-获取用户
  - DELETE-删除用户
  - PUT-修改用户
  - POST-保存用户
- 核心Filter；HiddenHttpMethodFilter

## 请求映射

- 四种请求映射方式;
  - `@GetMapping("/user")` 等价于 `@RequestMapping(value = "/user",method = RequestMethod.GET)`
  - `@PostMapping("/user")`  等价于 -》`@RequestMapping(value = "/user",method = RequestMethod.POST)`
  - `@PutMapping("/user")` 等价于 -》 `@RequestMapping(value = "/user",method = RequestMethod.PUT)`
  - `@DeleteMapping("/user")` 等价于 -》 `@RequestMapping(value = "/user",method = RequestMethod.DELETE)`

- **用法**
  - 开启页面表单的Rest功能
  - PUT请求或才DELETE请求 需要设置页面 form的属性method=post，并配置隐藏域`<input name="_method" type="hidden" value="PUT(DELETE)" />`（如果直接get或post，无需隐藏域）
  - 编写请求映射

## 实例

1. 在`application.yml`中开启表单的Rest功能

   ```yaml
   spring:
     mvc:
       hiddenmethod:
         filter:
           enabled: true # 如果提交的是表单，就需要开启。如果是客户端提交，如postman，可以不用开启。 postman可直接提交PUT或者DELETE请求
   ```

2. 配置页面

   该页面为测试页面 index.html， 放在 resource/static 目录下

   ```html
   <!DOCTYPE html>
   <html lang="cn">
   <head>
       <meta charset="UTF-8">
       <title>Title</title>
   </head>
   <body>
   <h1>SpringBoot MVC测试页</h1>
   测试REST风格：
   
   <form action="/user" method="get">
       <input value="REST-GET提交" type="submit" />
   </form>
   
   <form action="/user" method="post">
       <input value="REST-POST提交" type="submit" />
   </form>
   
   <form action="/user" method="post">
       <!-- 获取到 _method 方式提交 -->
       <input name="_method" type="hidden" value="DELETE"/>
       <input value="REST-DELETE 提交" type="submit"/>
   </form>
   
   <form action="/user" method="post">
       <!-- 获取到 _method 方式提交 -->
       <input name="_method" type="hidden" value="PUT" />
       <input value="REST-PUT提交"type="submit" />
   </form>
   <hr/>
   </body>
   </html>
   ```

3. 编写请求映射

   ```java
   package com.stanlong.controller;
   
   import org.springframework.web.bind.annotation.*;
   
   @RestController
   public class HelloController {
   
       @GetMapping("/user")
      // 等价于 -》@RequestMapping(value = "/user",method = RequestMethod.GET)
       public String getUser(){
           return "GET-张三";
       }
   
       @PostMapping("/user")
       //  等价于 -》@RequestMapping(value = "/user",method = RequestMethod.POST)
       public String saveUser(){
           return "POST-张三";
       }
   
       @PutMapping("/user")
       //  等价于 -》 @RequestMapping(value = "/user",method = RequestMethod.PUT)
       public String putUser(){
           return "PUT-张三";
       }
   
       @DeleteMapping("/user")
       //  等价于 -》 @RequestMapping(value = "/user",method = RequestMethod.DELETE)
       public String deleteUser(){
           return "DELETE-张三";
       }
   }
   ```

4. 启动主类访问页面

   页面正常响应请求映射的返回值。

   

