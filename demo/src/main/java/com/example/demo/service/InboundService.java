package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.pojo.Inventory;
import com.example.demo.pojo.Inventoryout;
import com.example.demo.pojo.Productinformation;
import com.example.demo.pojo.SumInventory;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface InboundService extends IService<Inventory> {

    PageInfo<Inventory> finduserPages(Integer pageNum, Integer pageSize, String barcodes, String productnames, String origins);

    PageInfo<Inventory> finduserPagesa(Integer pageNum, Integer pageSize, String barcodes, String productnames, String inbounddatea, String rangeesa, String rangeesb);

    List<Inventory> rangeestime();

    PageInfo<SumInventory> inventorya(Integer pageNum, Integer pageSize, String barcodes, String productnames);

    List<SumInventory> inventoryb();

   // PageInfo<reportdamage> finduserPagesb(Integer pageNum, Integer pageSize, String barcodes, String productnames, String inbounddatea, String rangeesa, String rangeesb);
}
