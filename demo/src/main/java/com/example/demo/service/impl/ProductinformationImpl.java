package com.example.demo.service.impl;

import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.exception.ServiceException;
import com.example.demo.mapper.ProductinformationMaper;
import com.example.demo.mapper.UserMaper;
import com.example.demo.pojo.AppUser;
import com.example.demo.pojo.Productinformation;
import com.example.demo.pojo.UpPw;
import com.example.demo.service.ProductinformationService;
import com.example.demo.service.UserService;
import com.example.demo.util.TokenUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductinformationImpl extends ServiceImpl<ProductinformationMaper, Productinformation> implements ProductinformationService {

    @Autowired
private ProductinformationMaper productinformationMaper;

    @Override
    public PageInfo<Productinformation> finduserPages(Integer pageNum, Integer pageSize, String barcode, String productname, String origin) {
        PageHelper.startPage(pageNum, pageSize);
        List<Productinformation> lists = productinformationMaper.eqProductinformationAll(barcode,productname,origin);
        PageInfo<Productinformation> pageInfo = new PageInfo<>(lists);
        return pageInfo;
    }

    @Override
    public String importData(MultipartFile file) throws IOException {
        // throw new ServiceException("401", "请登录");
        List<List<Object>> read = ExcelUtil.getReader(file.getInputStream()).read(0, Integer.MAX_VALUE);

        ArrayList<Productinformation> objects = new ArrayList<>();
        read.remove(0);
        for (List<Object> i:read) {
            Productinformation productinformation = new Productinformation();
            Long barcode = (Long) i.get(1);
            LambdaQueryWrapper<Productinformation> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Productinformation::getBarcode, barcode);
            List<Productinformation> list = this.list(lambdaQueryWrapper);
            if(!list.isEmpty()){
                throw new ServiceException("401", "条形码"+barcode+"已存在");
            }
            else {
               productinformation.setBarcode((Long) i.get(1));
                productinformation.setProductname((String) i.get(2));
                productinformation.setSpecifications((String) i.get(3));
                productinformation.setUnit((String) i.get(4));
                productinformation.setOrigin((String) i.get(5));
                productinformation.setRetailprice((Double) i.get(6));
                productinformation.setMerchant((String) i.get(7));
                objects.add(productinformation);
            }

        }
      this.saveBatch(objects);
        return "商品信息表导入成功";
    }



}



