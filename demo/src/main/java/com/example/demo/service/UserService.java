package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.pojo.AppUser;
import com.example.demo.pojo.UpPw;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService extends IService<AppUser> {

    AppUser login(AppUser user);


    void userService(UpPw user);

    void upuser(AppUser user);

    PageInfo<AppUser> selectuser(Integer pageNum, Integer pageSize, String username, String name);
}
