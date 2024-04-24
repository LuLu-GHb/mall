package com.example.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.*;
import com.example.entity.orders.OrdersDetail;
import com.example.mapper.OrdersMapper;
import com.example.service.*;

import com.example.utils.TokenUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author 有宇
* @description 针对表【orders(订单信息表)】的数据库操作Service实现
* @createDate 2024-04-04 20:00:59
*/
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders>
    implements OrdersService {
    @Resource
    private OrdersService ordersService;
    @Resource
    private BusinessService businessService;
    @Resource
    private GoodsService goodsService;
    @Resource
    private UserService userService;
    @Resource
    private AddressService addressService;

    @Override
    public IPage<OrdersDetail> selectPage1(Integer userId, Integer pageNum, Integer pageSize) {
        //1.创建分页构造器
        Page<Orders> pageInfo = new Page<>(pageNum, pageSize);
        //2.创建查询条件
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        Account curren = TokenUtils.getCurrentUser();
        if(curren.getRole().equals("BUSINESS")){
            queryWrapper.eq(Orders::getBusinessId, userId);
            //添加查询条件
        }

        //3.添加排序
        ordersService.page(pageInfo, queryWrapper);

        //创建RoomTypeDto的分类查询对象
        Page<OrdersDetail> dtoPage = new Page<>();
        //将pageInfo的数据拷贝到dtoPage中
        BeanUtils.copyProperties(pageInfo, dtoPage);

        List<Orders> records = pageInfo.getRecords();
        List<OrdersDetail> dtoList = records.stream().map((orders) -> {
            OrdersDetail ordersDetail = new OrdersDetail();

            BeanUtils.copyProperties(orders, ordersDetail);
            Goods goods = goodsService.getById(orders.getGoodsId());
            Business business = businessService.getById(goods.getBusinessId());
            User user = userService.getById(orders.getUserId());
            Address address = addressService.getById(orders.getAddressId());
            ordersDetail.setBusinessName(business.getName());
            ordersDetail.setGoodsName(goods.getName());
            ordersDetail.setGoodsImg(goods.getImg());
            ordersDetail.setGoodsUnit(goods.getUnit());
            ordersDetail.setGoodsPrice(goods.getPrice());
            ordersDetail.setUsername(user.getUsername());
            ordersDetail.setPhone(user.getPhone());
            ordersDetail.setUseraddress(address.getUseraddress());
            return ordersDetail;

        }).collect(Collectors.toList());
        dtoPage.setRecords(dtoList);
        return dtoPage;
    }

}




