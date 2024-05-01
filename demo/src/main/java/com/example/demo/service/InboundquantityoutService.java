package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.pojo.AppUser;
import com.example.demo.pojo.Inboundquantityout;
import com.example.demo.pojo.UpPw;
import com.github.pagehelper.PageInfo;

public interface InboundquantityoutService extends IService<Inboundquantityout> {

    PageInfo<Inboundquantityout> finduserPages(Integer pageNum, Integer pageSize, String barcod, String productname, String status,AppUser currentUser);
}
