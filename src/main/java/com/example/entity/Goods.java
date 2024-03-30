package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品信息表
 * @TableName goods
 */
@TableName(value ="goods")
@Data
public class Goods implements Serializable {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品主图
     */
    private String img;

    /**
     * 商品介绍
     */
    private String description;

    /**
     * 商品价格
     */
    private Double price;

    /**
     * 计件单位
     */
    private String unit;

    /**
     * 商品销量
     */
    private Integer count;

    /**
     * 分类ID
     */
    private Integer type_id;

    /**
     * 商家ID
     */
    private Integer business_id;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
