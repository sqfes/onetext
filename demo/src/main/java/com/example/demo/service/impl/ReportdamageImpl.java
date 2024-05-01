package com.example.demo.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.mapper.InboundMaper;
import com.example.demo.mapper.ReportdamageMaper;
import com.example.demo.pojo.Inventory;
import com.example.demo.pojo.Reportdamage;
import com.example.demo.pojo.SumInventory;
import com.example.demo.service.InboundService;
import com.example.demo.service.ReportdamageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportdamageImpl extends ServiceImpl<ReportdamageMaper, Reportdamage> implements ReportdamageService {

}
