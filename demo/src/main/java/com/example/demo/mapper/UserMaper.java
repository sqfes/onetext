package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.pojo.AppUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMaper extends BaseMapper<AppUser> {

    AppUser selectByUsername(String username);

    List<AppUser> selectusermapper(@Param("username") String username,@Param("name") String name);
}
