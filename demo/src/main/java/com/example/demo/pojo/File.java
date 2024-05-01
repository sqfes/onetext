package com.example.demo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("productinformation")
public class File extends Model<File> {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;//id
    private Long  barcode; //条形码
    private String  productname;//产品名称
    private String  specifications;//规格
    private String  unit;//单位
    private String  origin;//产地
    private Double  retailprice;//零售价
//    private String  merchant;//商家商户
}
