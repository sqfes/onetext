package com.example.demo.service.impl;

import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.exception.ServiceException;
import com.example.demo.mapper.UserMaper;
import com.example.demo.pojo.AppUser;
import com.example.demo.pojo.UpPw;
import com.example.demo.service.UserService;
import com.example.demo.util.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@Service
public class UserImpl extends ServiceImpl<UserMaper, AppUser> implements UserService {
    @Resource
   private UserMaper userMapper;
    @Override
    public AppUser login(AppUser user) {
        AppUser useres = userMapper.selectByUsername(user.getUsername());
        if (useres == null) {
            // 抛出一个自定义的异常
            throw new ServiceException("用户名或密码错误");
        }
        if (!user.getPassword().equals(useres.getPassword())) {
            throw new ServiceException("用户名或密码错误");
        }
        String token = TokenUtils.genToken(useres.getId().toString(), useres.getPassword());
        useres.setToken(token);
        return useres;
    }

    @Override
    public void userService(UpPw user) {
        String confirmPassword = user.getConfirmPassword();
        AppUser currentUser = TokenUtils.getCurrentUser();

        LambdaUpdateWrapper<AppUser> lambdaUpdateWrappera = new LambdaUpdateWrapper<>();
        lambdaUpdateWrappera
                .set(AppUser::getPassword,confirmPassword)
                .eq(AppUser::getId,currentUser.getId());
        AppUser newInfo = new AppUser();
        this.update(newInfo,lambdaUpdateWrappera);
    }

    @Override
    public void upuser(AppUser user) {
        LambdaUpdateWrapper<AppUser> lambdaUpdateWrappera = new LambdaUpdateWrapper<>();
        lambdaUpdateWrappera
                .set(AppUser::getName,user.getName())
                .set(AppUser::getPhone,user.getPhone())
                .set(AppUser::getEmail,user.getEmail())
                .set(AppUser::getAvatar,user.getAvatar())
                .eq(AppUser::getId,user.getId());
        AppUser newInfo = new AppUser();
        this.update(newInfo,lambdaUpdateWrappera);
    }

    @Override
    public PageInfo<AppUser> selectuser(Integer pageNum, Integer pageSize, String username, String name) {
        PageHelper.startPage(pageNum, pageSize);
        List<AppUser> appUsers = userMapper.selectusermapper(username, name);
        PageInfo<AppUser> pageInfo = new PageInfo<>(appUsers);

        return pageInfo;
    }


}
