package com.stanlong;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class AdminApplicationTests {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    private DataSource dataSource; // 自定义配置的数据源

    @Test
    public void contextLoads() {
        Long aLong = jdbcTemplate.queryForObject("select count(*) from tb_user", Long.class);
        log.info("记录总数：{}",aLong);
        log.info("数据源类型：" + dataSource.getClass()); // 数据源类型：class com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceWrapper
    }
}
