package com.stanlong.mapper;

import com.stanlong.bean.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User getUser(long userId);
}
