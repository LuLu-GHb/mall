package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.Result;
import com.example.entity.*;
import com.example.entity.cart.CartDetail;
import com.example.entity.orders.OrdersDetail;
import com.example.service.*;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrdersController {

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

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Orders orders) {
        ordersService.save(orders);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        ordersService.removeById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        ordersService.removeByIds(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody Orders orders) {
        ordersService.updateById(orders);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Orders orders = ordersService.getById(id);
        return Result.success(orders);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Orders orders ) {
        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>(orders);
        List<Orders> list = ordersService.list(queryWrapper);
        return Result.success(list);
    }

//    /**
//     * 分页查询
//     */
//    @GetMapping("/selectPage")
//    public Result selectPage(Orders orders,
//                             @RequestParam(defaultValue = "1") Integer pageNum,
//                             @RequestParam(defaultValue = "10") Integer pageSize) {
//        Page<Orders> page = new Page<>(pageNum, pageSize);
//        LambdaQueryWrapper<Orders> lqw = new LambdaQueryWrapper<>();
//        // 添加条件判断，如果查询条件不为空，则进行模糊查询
////        if (StringUtils.isNotBlank(orders.getStatus())) {
////            lqw.like(Orders::getUsername, business.getUsername());
////        }
//
//        IPage<Orders> resultpage = ordersService.page(page,lqw);
//        return Result.success(resultpage);
//    }
    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Integer userId,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
//        //1.创建分页构造器
//        Page<Orders> pageInfo = new Page<>(pageNum, pageSize);
//        //2.创建查询条件
//        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(Orders::getUserId, userId);
//        //添加查询条件
//        //3.添加排序
//        ordersService.page(pageInfo, queryWrapper);
//
//        //创建RoomTypeDto的分类查询对象
//        Page<OrdersDetail> dtoPage = new Page<>();
//        //将pageInfo的数据拷贝到dtoPage中
//        BeanUtils.copyProperties(pageInfo, dtoPage);
//
//        List<Orders> records = pageInfo.getRecords();
//        List<OrdersDetail> dtoList = records.stream().map((orders) -> {
//            OrdersDetail ordersDetail = new OrdersDetail();
//
//            BeanUtils.copyProperties(orders, ordersDetail);
//            Goods goods = goodsService.getById(orders.getGoodsId());
//            Business business = businessService.getById(goods.getBusinessId());
//            User user = userService.getById(orders.getUserId());
//            Address address = addressService.getById(orders.getAddressId());
//            ordersDetail.setBusinessName(business.getName());
//            ordersDetail.setGoodsName(goods.getName());
//            ordersDetail.setGoodsImg(goods.getImg());
//            ordersDetail.setGoodsUnit(goods.getUnit());
//            ordersDetail.setGoodsPrice(goods.getPrice());
//            ordersDetail.setUsername(user.getUsername());
//            ordersDetail.setPhone(user.getPhone());
//            ordersDetail.setUseraddress(address.getUseraddress());
//            return ordersDetail;
//
//        }).collect(Collectors.toList());
//        dtoPage.setRecords(dtoList);
        return Result.success(ordersService.selectPage1(userId, pageNum, pageSize));

    }

}
