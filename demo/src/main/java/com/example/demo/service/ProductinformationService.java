package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.pojo.Productinformation;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface ProductinformationService extends IService<Productinformation> {

    PageInfo<Productinformation> finduserPages(Integer pageNum, Integer pageSize, String barcode, String productname, String origin);

    String importData(MultipartFile file) throws IOException;


}
