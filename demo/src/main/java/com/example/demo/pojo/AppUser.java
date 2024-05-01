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
@TableName("user")
public class AppUser extends Model<AppUser> {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;//id
    private String  name; //姓名
    private Integer employeenumber;//员工编号
    private String  age;//年龄
    private String sex;//性别
    private String phone;//电话
    private String email;//邮箱
    private String username;//用户名
    private String password;//密码
    private String role;//角色
    private String avatar;//头像
    //是否为数据库表字段注解
    @TableField(exist = false)
    private String token;//token

}
