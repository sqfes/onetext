package com.example.demo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("reportdamage")
public class Reportdamage extends Model<Reportdamage>{
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;//id
    private Long  barcode; //条形码
    private String  productname;//产品名称
    private String  specifications;//规格
    private String  unit;//单位
    private String  origin;//产地
    private Double  retailprice;//零售价
    @TableField(exist = false)
    private String  username;//入库人
    private String  inbounddate;//入库时间
    private Integer  inboundquantity;//报损数量
    private String  rangees;//过期时间
    private Integer  inboundquantityout=0;
    private String reason;//报损原因
    private String remarks;//备注
    private String damageclaimant;//负责人
    private int  status;//状态
    private String  merchant;//供应商
    private String  reportdamagedata;//报损时间

}
