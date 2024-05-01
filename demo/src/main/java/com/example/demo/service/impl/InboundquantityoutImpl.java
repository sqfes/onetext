package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.exception.ServiceException;
import com.example.demo.mapper.InboundquantityoutMaper;
import com.example.demo.mapper.UserMaper;
import com.example.demo.pojo.AppUser;
import com.example.demo.pojo.Inboundquantityout;
import com.example.demo.pojo.Productinformation;
import com.example.demo.pojo.UpPw;
import com.example.demo.service.InboundquantityoutService;
import com.example.demo.service.UserService;
import com.example.demo.util.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class InboundquantityoutImpl extends ServiceImpl<InboundquantityoutMaper, Inboundquantityout> implements InboundquantityoutService {

    @Autowired
    private  InboundquantityoutMaper inboundquantityoutMaper;

    @Override
    public PageInfo<Inboundquantityout> finduserPages(Integer pageNum, Integer pageSize, String barcod, String productname, String status,AppUser currentUser) {
        PageHelper.startPage(pageNum, pageSize);
        String role = currentUser.getRole();
        String merchant = currentUser.getUsername();
        List<Inboundquantityout> lists = null;
        if(role.equals("2")){
           lists = inboundquantityoutMaper.eqinboundquantityoutAlla(barcod,productname,status,merchant);
        }else {
            lists = inboundquantityoutMaper.eqinboundquantityoutAll(barcod,productname,status);
        }

        PageInfo<Inboundquantityout> pageInfo = new PageInfo<>(lists);
        return pageInfo;
    }
}
