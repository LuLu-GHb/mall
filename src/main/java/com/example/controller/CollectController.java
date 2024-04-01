package com.example.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.Result;
import com.example.common.enums.ResultCodeEnum;
import com.example.entity.Business;
import com.example.entity.Collect;
import com.example.entity.Goods;
import com.example.entity.Typee;
import com.example.entity.collect.CollectDetail;
import com.example.exception.CustomException;
import com.example.service.BusinessService;
import com.example.service.CollectService;
import com.example.service.GoodsService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/collect")
public class CollectController {

    @Resource
    private CollectService collectService;
    @Resource
    private GoodsService goodsService;
    @Resource
    private BusinessService businessService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Collect collect) {
        // 判断一下该用户有没有收藏过该商品，如果有，就要提示用户不能重复收藏
        LambdaQueryWrapper<Collect> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Collect::getUserId, collect.getUserId());
        queryWrapper.eq(Collect::getGoodsId, collect.getGoodsId());
        if (collectService.count(queryWrapper) > 0) {
            throw new CustomException(ResultCodeEnum.COLLECT_ALREADY_ERROR);
        }
        collectService.save(collect);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        collectService.removeById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        collectService.removeByIds(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody Collect collect) {
        collectService.updateById(collect);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Collect collect = collectService.getById(id);
        return Result.success(collect);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Collect collect ) {
        QueryWrapper<Collect> queryWrapper = new QueryWrapper<>(collect);
        List<Collect> list = collectService.list(queryWrapper);
        return Result.success(list);
    }

/*    *//**
     * 分页查询
     *//*
    @GetMapping("/selectPage")
    public Result selectPage(Collect collect,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Collect> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Collect> lqw = new LambdaQueryWrapper<>();
        // 添加条件判断，如果查询条件不为空，则进行模糊查询
        if (StringUtils.isNotBlank(business.getUsername())) {
            lqw.like(Collect::getUsername, business.getUsername());
        }

        IPage<CollectDetail> resultpage = businessService.page(page,lqw);
        return Result.success(resultpage);
    }*/

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Integer userId,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        //1.创建分页构造器
        Page<Collect> pageInfo = new Page<>(pageNum, pageSize);
        //2.创建查询条件
        LambdaQueryWrapper<Collect> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Collect::getUserId, userId);
//添加查询条件
//3.添加排序
        collectService.page(pageInfo, queryWrapper);

        //创建RoomTypeDto的分类查询对象
        Page<CollectDetail> dtoPage = new Page<>();
        //将pageInfo的数据拷贝到dtoPage中
        BeanUtils.copyProperties(pageInfo, dtoPage);

        List<Collect> records = pageInfo.getRecords();
        List<CollectDetail> dtoList = records.stream().map((collect1) -> {
            CollectDetail collectDetail = new CollectDetail();

            BeanUtils.copyProperties(collect1, collectDetail);
            Goods goods = goodsService.getById(collect1.getGoodsId());
            Business business = businessService.getById(goods.getBusinessId());
            collectDetail.setGoodsName(goods.getName());
            collectDetail.setBusinessName(business.getName());
            collectDetail.setGoodsImg(goods.getImg());
            collectDetail.setGoodsPrice(goods.getPrice());
            collectDetail.setGoodUnit(goods.getUnit());
            return collectDetail;

        }).collect(Collectors.toList());
        dtoPage.setRecords(dtoList);
        return Result.success(dtoPage);

    }

}


