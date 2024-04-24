package com.example.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.Comment;
import com.example.entity.comment.CommentDetail;
import com.example.entity.orders.OrdersDetail;

/**
* @author 有宇
* @description 针对表【comment(评论信息表)】的数据库操作Service
* @createDate 2024-04-06 13:23:58
*/
public interface CommentService extends IService<Comment> {

    IPage selectPage1(Integer userId,Integer pageNum, Integer pageSize);

}
