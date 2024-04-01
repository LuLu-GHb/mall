package com.example.entity.cart;

import com.example.entity.Cart;
import com.example.entity.Goods;
import lombok.Data;

/**
 * 有宇
 * 2024/4/1 12:32
 */
@Data
public class CartDetail extends Cart {
    private String businessName;
    private String goodsName;
    private String goodsImg;
    private String goodUnit;
    private Double goodsPrice;
}
