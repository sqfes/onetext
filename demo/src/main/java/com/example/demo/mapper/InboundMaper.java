package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.pojo.File;
import com.example.demo.pojo.Inventory;
import com.example.demo.pojo.SumInventory;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface InboundMaper extends BaseMapper<Inventory> {

    List<Inventory> eqProductinformationAll(@Param("barcodes") String barcodes,@Param("productnames") String productnames,@Param("inbounddate") String inbounddate,@Param("rangeesa") String rangeesa,@Param("rangeesb") String rangeesb);
    @Select("SELECT * FROM inventory WHERE rangees < CURDATE()")
    List<Inventory> rangeestime();

    List<SumInventory> inventorya(@Param("barcodes") String barcodes,@Param("productnames") String productnames);
@Select("SELECT barcode, productname,SUM(inboundquantity) AS suminboundquantity FROM inventory  GROUP BY barcode, productname")
    List<SumInventory> inventoryb();
    @Select("SELECT sum(inboundquantity) FROM `inventory`")
    Integer eq();
    @Select("SELECT sum(inboundquantity) FROM inventory WHERE rangees < CURDATE();")
    Integer eq1();
@Select("SELECT sum(inboundquantity) FROM inventory  WHERE rangees BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 1 MONTH)")
    Integer eq2();
}
