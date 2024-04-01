package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.Result;
import com.example.entity.*;
import com.example.entity.cart.CartDetail;
import com.example.entity.collect.CollectDetail;
import com.example.service.AddressService;
import com.example.service.BusinessService;
import com.example.service.CartService;
import com.example.service.GoodsService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Resource
    private CartService cartService;
    @Resource
    private BusinessService businessService;
    @Resource
    private GoodsService goodsService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Cart cart) {
        cartService.save(cart);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        cartService.removeById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        cartService.removeByIds(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody Cart cart) {
        cartService.updateById(cart);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Cart cart = cartService.getById(id);
        return Result.success(cart);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Cart cart ) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>(cart);
        List<Cart> list = cartService.list(queryWrapper);
        return Result.success(list);
    }

//    /**
//     * 分页查询
//     */
//    @GetMapping("/selectPage")
//    public Result selectPage(Integer userId,
//                             @RequestParam(defaultValue = "1") Integer pageNum,
//                             @RequestParam(defaultValue = "10") Integer pageSize) {
//        Page<Cart> page = new Page<>(pageNum, pageSize);
//        LambdaQueryWrapper<Cart> lqw = new LambdaQueryWrapper<>();
//        // 添加条件判断，如果查询条件不为空，则进行模糊查询
////        if (StringUtils.isNotBlank(business.getUsername())) {
////            lqw.like(Address::getUsername, business.getUsername());
////        }
//        lqw.eq(Cart::getUserId,userId);
//        IPage<Cart> resultpage = cartService.page(page,lqw);
//        return Result.success(resultpage);
//
//    }
    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Integer userId,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        //1.创建分页构造器
        Page<Cart> pageInfo = new Page<>(pageNum, pageSize);
        //2.创建查询条件
        LambdaQueryWrapper<Cart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Cart::getUserId, userId);
//添加查询条件
//3.添加排序
        cartService.page(pageInfo, queryWrapper);

        //创建RoomTypeDto的分类查询对象
        Page<CartDetail> dtoPage = new Page<>();
        //将pageInfo的数据拷贝到dtoPage中
        BeanUtils.copyProperties(pageInfo, dtoPage);

        List<Cart> records = pageInfo.getRecords();
        List<CartDetail> dtoList = records.stream().map((cart) -> {
            CartDetail cartDetail = new CartDetail();

            BeanUtils.copyProperties(cart, cartDetail);
            Goods goods = goodsService.getById(cart.getGoodsId());
            Business business = businessService.getById(goods.getBusinessId());
            cartDetail.setBusinessName(business.getName());
            cartDetail.setGoodsName(goods.getName());
            cartDetail.setGoodsImg(goods.getImg());
            cartDetail.setGoodsPrice(goods.getPrice());
            cartDetail.setGoodUnit(goods.getUnit());
            return cartDetail;

        }).collect(Collectors.toList());
        dtoPage.setRecords(dtoList);
        return Result.success(dtoPage);

    }
}
