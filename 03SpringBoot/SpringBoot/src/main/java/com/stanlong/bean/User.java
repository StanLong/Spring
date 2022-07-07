package com.stanlong.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
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