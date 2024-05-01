package com.example.demo.pojo;

import cn.hutool.core.annotation.Alias;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("productinformation")
public class Productinformation extends Model<Productinformation> {

//    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;//id
   // @ExcelProperty(value = "条形码", index = 1)
    private Long  barcode; //条形码
   // @ExcelProperty(value = "产品名称", index = 2)
    private String  productname;//产品名称
   // @ExcelProperty(value = "规格", index = 3)
    private String  specifications;//规格
   // @ExcelProperty(value = "单位", index = 4)
    private String  unit;//单位
   // @ExcelProperty(value = "产地", index = 5)
    private String  origin;//产地
    //@ExcelProperty(value = "零售价", index = 6)
    private Double  retailprice;//零售价
    private String  merchant;//商家商户
    @TableField(exist = false)
    private int purchasequantity;//进库数量
}
