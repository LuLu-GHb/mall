package com.example.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.Orders;
import com.example.entity.orders.OrdersDetail;


/**
* @author 有宇
* @description 针对表【orders(订单信息表)】的数据库操作Service
* @createDate 2024-04-04 20:00:59
*/
public interface OrdersService extends IService<Orders> {
    IPage<OrdersDetail> selectPage1(Integer userId, Integer pageNum, Integer pageSize);

}
