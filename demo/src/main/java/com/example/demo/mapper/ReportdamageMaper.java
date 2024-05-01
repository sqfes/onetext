package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.pojo.Inventory;
import com.example.demo.pojo.Reportdamage;
import com.example.demo.pojo.SumInventory;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ReportdamageMaper extends BaseMapper<Reportdamage> {
  //  @Select("SELECT * FROM `reportdamage` WHERE barcode=#{barcode1} AND inbounddate=#{inbounddate1} AND rangees=#{rangees1}")
    Reportdamage  eqnotreportdamage(@Param("id") Long id,@Param("barcode") Long barcode,@Param("inbounddate1") String inbounddate1,@Param("rangees1") String rangees1);
    @Select("SELECT COALESCE(SUM(inboundquantity),0) FROM reportdamage WHERE reportdamagedata >= DATE_FORMAT(CURDATE() ,'%Y-%m-01') AND reportdamagedata < DATE_ADD(DATE_FORMAT(CURDATE() ,'%Y-%m-01'), INTERVAL 1 MONTH) and reason='变质' and status='3'")
  int eq();
  @Select("SELECT COALESCE(SUM(inboundquantity),0) FROM reportdamage WHERE reportdamagedata >= DATE_FORMAT(CURDATE() ,'%Y-%m-01') AND reportdamagedata < DATE_ADD(DATE_FORMAT(CURDATE() ,'%Y-%m-01'), INTERVAL 1 MONTH) and reason='过期' and status='3'")
  int eq1();
  @Select("SELECT COALESCE(SUM(inboundquantity),0) FROM reportdamage WHERE reportdamagedata >= DATE_FORMAT(CURDATE() ,'%Y-%m-01') AND reportdamagedata < DATE_ADD(DATE_FORMAT(CURDATE() ,'%Y-%m-01'), INTERVAL 1 MONTH) and reason='其它' and status='3'")
  int eq2();
}
