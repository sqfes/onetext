package com.example.demo.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
@TableName("inventory")
public class SumInventory {
    private Long  barcode; //条形码
    private String  productname;//产品名称
    private Integer  suminboundquantity;//规格
}
