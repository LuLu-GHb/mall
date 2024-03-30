package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 商家信息表
 * @TableName business
 */
@TableName(value ="business")
@Data
public class Business{
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 店铺名
     */
    private String name;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 角色标识
     */
    private String role;

    /**
     * 电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 商家介绍
     */
    private String description;

    /**
     * 审核状态
     */
    private String status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
