package com.example.entity.orders;

import com.example.entity.Cart;
import com.example.entity.Orders;
import lombok.Data;

import java.util.List;

/**
 * 有宇
 * 2024/4/1 12:32
 */
@Data
public class OrdersDetail extends Orders {
//    private List<Cart> cartData;


    private String businessName;
    private String goodsName;
    private String goodsImg;
    private String goodsUnit;
    private Double goodsPrice;
    private String username;
    private String useraddress;
    private String phone;
}
