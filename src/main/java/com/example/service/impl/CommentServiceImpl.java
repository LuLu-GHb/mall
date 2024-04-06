package com.example.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.*;
import com.example.entity.comment.CommentDetail;
import com.example.entity.orders.OrdersDetail;
import com.example.mapper.CommentMapper;
import com.example.service.BusinessService;
import com.example.service.CommentService;
import com.example.service.GoodsService;
import com.example.service.UserService;
import com.example.utils.TokenUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author 有宇
* @description 针对表【comment(评论信息表)】的数据库操作Service实现
* @createDate 2024-04-06 13:23:58
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService {
@Resource
private CommentService commentService;
@Resource
private GoodsService goodsService;
@Resource
private BusinessService businessService;
@Resource
private UserService userService;



    @Override
    public Object selectPage1(Integer pageNum, Integer pageSize) {
        //1.创建分页构造器
        Page<Comment> pageInfo = new Page<>(pageNum, pageSize);
        //2.创建查询条件
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件
        //3.添加排序
        commentService.page(pageInfo, queryWrapper);
        //创建RoomTypeDto的分类查询对象
        Page<CommentDetail> dtoPage = new Page<>();
        //将pageInfo的数据拷贝到dtoPage中
        BeanUtils.copyProperties(pageInfo, dtoPage);

        List<Comment> records = pageInfo.getRecords();
        List<CommentDetail> dtoList = records.stream().map((comment) -> {
            CommentDetail commentDetail = new CommentDetail();

            BeanUtils.copyProperties(comment, commentDetail);
            Goods goods = goodsService.getById(comment.getGoodsId());
            Business business = businessService.getById(goods.getBusinessId());
            User user = userService.getById(comment.getUserId());
            commentDetail.setBusinessName(business.getName());
            commentDetail.setGoodsName(goods.getName());
            commentDetail.setUserName(user.getUsername());
            commentDetail.setUserAvatar(user.getAvatar());
            return commentDetail;

        }).collect(Collectors.toList());
        dtoPage.setRecords(dtoList);
        return dtoPage;
    }

    @Override
    public Object selectPage2(Integer businessId, Integer pageNum, Integer pageSize) {
        //1.创建分页构造器
        Page<Comment> pageInfo = new Page<>(pageNum, pageSize);
        //2.创建查询条件
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getBusinessId, businessId);
        //添加查询条件
        //3.添加排序
        commentService.page(pageInfo, queryWrapper);
        //创建RoomTypeDto的分类查询对象
        Page<CommentDetail> dtoPage = new Page<>();
        //将pageInfo的数据拷贝到dtoPage中
        BeanUtils.copyProperties(pageInfo, dtoPage);

        List<Comment> records = pageInfo.getRecords();
        List<CommentDetail> dtoList = records.stream().map((comment) -> {
            CommentDetail commentDetail = new CommentDetail();

            BeanUtils.copyProperties(comment, commentDetail);
            Goods goods = goodsService.getById(comment.getGoodsId());
            Business business = businessService.getById(goods.getBusinessId());
            User user = userService.getById(comment.getUserId());
            commentDetail.setBusinessName(business.getName());
            commentDetail.setGoodsName(goods.getName());
            commentDetail.setUserName(user.getUsername());
            commentDetail.setUserAvatar(user.getAvatar());
            return commentDetail;

        }).collect(Collectors.toList());
        dtoPage.setRecords(dtoList);
        return dtoPage;
    }
    @Override
    public Page<CommentDetail> selectPage3(Integer goodsId, Integer pageNum, Integer pageSize) {
        //1.创建分页构造器
        Page<Comment> pageInfo = new Page<>(pageNum, pageSize);
        //2.创建查询条件
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getGoodsId, goodsId);
        //添加查询条件
        //3.添加排序
        commentService.page(pageInfo, queryWrapper);
        //创建RoomTypeDto的分类查询对象
        Page<CommentDetail> dtoPage = new Page<>();
        //将pageInfo的数据拷贝到dtoPage中
        BeanUtils.copyProperties(pageInfo, dtoPage);

        List<Comment> records = pageInfo.getRecords();
        List<CommentDetail> dtoList = records.stream().map((comment) -> {
            CommentDetail commentDetail = new CommentDetail();

            BeanUtils.copyProperties(comment, commentDetail);
            Goods goods = goodsService.getById(comment.getGoodsId());
            Business business = businessService.getById(goods.getBusinessId());
            User user = userService.getById(comment.getUserId());
            commentDetail.setBusinessName(business.getName());
            commentDetail.setGoodsName(goods.getName());
            commentDetail.setUserName(user.getUsername());
            commentDetail.setUserAvatar(user.getAvatar());
            return commentDetail;

        }).collect(Collectors.toList());
        dtoPage.setRecords(dtoList);
        return dtoPage;
    }
}




