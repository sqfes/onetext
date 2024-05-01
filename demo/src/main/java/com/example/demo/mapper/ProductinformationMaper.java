package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.pojo.AppUser;
import com.example.demo.pojo.Productinformation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductinformationMaper extends BaseMapper<Productinformation> {

    List<Productinformation> eqProductinformationAll(@Param("barcode") String barcode,@Param("productname")  String productname,@Param("origin")  String origin);
}
