package com.stanlong.config;

import com.stanlong.bean.Car;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 *
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(Car.class)
public class MyConfig {

}
