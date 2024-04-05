package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 订单信息表
 * @TableName orders
 */
@TableName(value ="orders")
@Data
public class Orders implements Serializable {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 订单ID
     */
    @TableField(value = "order_id")
    private String orderId;

    /**
     * 商品ID
     */
    @TableField(value = "goods_id")
    private Integer goodsId;

    /**
     * 商家ID
     */
    @TableField(value = "business_id")
    private Integer businessId;

    /**
     * 商品数量
     */
    private Integer num;

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    private Integer userId;

    /**
     * 订单价格
     */
    private Double price;

    /**
     * 地址ID
     */
    @TableField(value = "address_id")
    private Integer addressId;

    /**
     * 订单状态
     */
    private String status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
