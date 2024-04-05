package com.example.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Orders;
import com.example.mapper.OrdersMapper;
import com.example.service.OrdersService;
import org.springframework.stereotype.Service;

/**
* @author 有宇
* @description 针对表【orders(订单信息表)】的数据库操作Service实现
* @createDate 2024-04-04 20:00:59
*/
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders>
    implements OrdersService {

}




