package com.stanlong.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @MatrixVariable 矩阵变量
 */
@RestController
public class ParameterController {

    // 第一种矩阵变量形式
    // cars/sell;low=34;brand=byd,audi,yd
    @GetMapping("/cars/{path}")
    public Map carsSell(@MatrixVariable("low") Integer low
            , @MatrixVariable("brand") List<String> brand
            , @PathVariable String path){
        Map<String, Object> map = new HashMap<>();
        map.put("low", low);
        map.put("brand", brand);
        map.put("path", path);
        return map;
    }

    // 第二种矩阵变量形式
    // 矩阵变量中有多个相同的名字：  /boss/1;age=20/2;age=10
    @GetMapping("/boss/{bossAge}/{empAge}")
    public Map boss(@MatrixVariable(value = "age", pathVar = "bossAge") Integer bossAge,
                    @MatrixVariable(value = "age", pathVar = "empAge") Integer empAge){
        Map<String, Object> map = new HashMap<>();
        map.put("bossAge", bossAge);
        map.put("empAge", empAge);
        return map;
    }
}
