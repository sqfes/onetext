package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.pojo.AppUser;
import com.example.demo.pojo.Inboundquantityout;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface InboundquantityoutMaper extends BaseMapper<Inboundquantityout> {

    List<Inboundquantityout> eqinboundquantityoutAll(@Param("barcode") String barcod,@Param("productname") String productname,@Param("status") String status);

    List<Inboundquantityout> eqinboundquantityoutAlla(@Param("barcode") String barcod,@Param("productname") String productname,@Param("status") String status,@Param("merchant") String merchant);

    @Select("SELECT COALESCE(SUM(purchasequantity),0) FROM inboundquantityout WHERE inbounddate >= DATE_FORMAT(CURDATE() ,'%Y-%m-01') AND inbounddate < DATE_ADD(DATE_FORMAT(CURDATE() ,'%Y-%m-01'), INTERVAL 1 MONTH) and status='2'")
    int eq1();
    @Select("SELECT COALESCE(SUM(purchasequantity),0) FROM inboundquantityout WHERE inbounddate >= DATE_FORMAT(CURDATE() ,'%Y-%m-01') AND inbounddate < DATE_ADD(DATE_FORMAT(CURDATE() ,'%Y-%m-01'), INTERVAL 1 MONTH) and status='4'")
    int eq2();
@Insert("insert into inboundquantityout(barcode,productname,specifications,unit,retailprice,merchant,username,origin,status,purchasequantity,remarks,inbounddate) values(#{barcode},#{productname},#{specifications},#{unit},#{retailprice},#{merchant},#{username},#{origin},#{status},#{purchasequantity},#{remarks},#{inbounddate})")
    void save(Inboundquantityout inboundquantityout1);
}
