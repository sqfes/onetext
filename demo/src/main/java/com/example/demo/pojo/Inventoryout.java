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
public class Inventoryout extends Model<Inventoryout>{
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;//id
    private Long  barcode; //条形码
    private String  productname;//产品名称
    private String  specifications;//规格
    private String  unit;//单位
    private String  origin;//产地
    private Double  retailprice;//零售价
    private String  username;//入库人
    private String  inbounddate;//出入库时间
    private Integer  inboundquantity;//入库数量
    private String  rangees;//出库时间
    @TableField(exist = false)
    private Integer  inboundquantityout;


}
