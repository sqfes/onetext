package com.example.demo.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.mapper.InboundMaper;
import com.example.demo.mapper.StocklogMaper;
import com.example.demo.pojo.Inventory;
import com.example.demo.pojo.Stocklog;
import com.example.demo.pojo.SumInventory;
import com.example.demo.service.InboundService;
import com.example.demo.service.StocklogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StocklogImpl extends ServiceImpl<StocklogMaper, Stocklog> implements StocklogService {

    @Autowired
    private StocklogMaper stocklogMaper;

    @Override
    public  PageInfo<Stocklog> getstocklogser(Integer pageNum, Integer pageSize, String productname, String outinbounddate, String outin) {
        PageHelper.startPage(pageNum,pageSize);
        List<Stocklog> stocklogList = stocklogMaper.getstocklogmapper(productname,outinbounddate,outin);
        PageInfo<Stocklog> pageInfo = new PageInfo<>(stocklogList);
        return pageInfo;
    }
}
