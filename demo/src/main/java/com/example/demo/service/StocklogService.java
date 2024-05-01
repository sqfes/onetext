package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.pojo.Stocklog;
import com.github.pagehelper.PageInfo;

import java.util.List;


public interface StocklogService extends IService<Stocklog> {

    PageInfo<Stocklog> getstocklogser(Integer pageNum, Integer pageSize, String productname, String outinbounddate, String outin);
}
