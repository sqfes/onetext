package com.example.demo.service.impl;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.mapper.InboundMaper;
import com.example.demo.pojo.Inventory;
import com.example.demo.pojo.Inventoryout;
import com.example.demo.pojo.Productinformation;
import com.example.demo.pojo.SumInventory;
import com.example.demo.service.InboundService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InboundImpl extends ServiceImpl<InboundMaper, Inventory> implements InboundService {
@Autowired
private InboundMaper inboundMaper;
    @Override
    public PageInfo<Inventory> finduserPages(Integer pageNum, Integer pageSize, String barcodes, String productnames, String origins) {
        return null;
    }

    @Override
    public PageInfo<Inventory> finduserPagesa(Integer pageNum, Integer pageSize, String barcodes, String productnames, String inbounddatea, String rangeesa, String rangeesb) {
        PageHelper.startPage(pageNum, pageSize);
        String inbounddate=inbounddatea;
        List<Inventory> lists = inboundMaper.eqProductinformationAll(barcodes,productnames,inbounddate,rangeesa,rangeesb);
        PageInfo<Inventory> pageInfo = new PageInfo<>(lists);
        return pageInfo;
    }

    @Override
    public List<Inventory> rangeestime() {
        List<Inventory> lists = inboundMaper.rangeestime();
        return lists;
    }

    @Override
    public PageInfo<SumInventory> inventorya(Integer pageNum, Integer pageSize, String barcodes, String productnames) {
     //   SumInventory sumInventory = inboundMaper.inventorya();

        PageHelper.startPage(pageNum, pageSize);
        List<SumInventory> lists =  inboundMaper.inventorya(barcodes,productnames);
        PageInfo<SumInventory> pageInfo = new PageInfo<>(lists);
        return pageInfo;
    }

    @Override
    public List<SumInventory> inventoryb() {
        List<SumInventory> lists = inboundMaper.inventoryb();
        return lists;
    }




}
