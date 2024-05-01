package com.example.demo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.extension.activerecord.Model;
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("inventory")
public class Inventory extends Model<Inventory>{
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;//id
    private Long  barcode; //条形码
    private String  productname;//产品名称
    private String  specifications;//规格
    private String  unit;//单位
    private String  origin;//产地
    private Double  retailprice;//零售价
    private String  username;//入库人
    private String  inbounddate;//入库时间
    private Integer  inboundquantity;//入库数量
    private String  rangees;//过期时间
    @TableField(exist = false)
    private Integer  inboundquantityout=0;


}
