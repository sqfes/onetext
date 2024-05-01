package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.pojo.BigScreenes.OutboundRanking;
import com.example.demo.pojo.Inventory;
import com.example.demo.pojo.Stocklog;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StocklogMaper extends BaseMapper<Stocklog> {
    List<Stocklog> getstocklogmapper(@Param("productname")  String productname,@Param("outinbounddate") String outinbounddate,@Param("outin") String outin);
@Select("SELECT sum(outinboundquantity) FROM `stocklog` WHERE outinbounddate LIKE CONCAT(CURDATE(), '%') and outin='入库'")
    Integer eq();
@Select("SELECT sum(outinboundquantity) FROM `stocklog` WHERE outinbounddate LIKE CONCAT(CURDATE(), '%') and outin='出库'")
    Integer eq1 ();
@Select("SELECT * FROM(SELECT productname as name,sum(outinboundquantity) as value  FROM (SELECT * FROM stocklog WHERE YEAR(outinbounddate) = YEAR(CURDATE()) AND MONTH(outinbounddate) = MONTH(CURDATE())) as a GROUP BY productname,outin  HAVING  outin='出库') as b ORDER BY value DESC LIMIT 5")
    List<OutboundRanking> eqoutboundranking();
}
