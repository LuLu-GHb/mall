package com.example.service.impl;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.*;
import com.example.mapper.*;
import com.example.service.GoodsService;
import com.example.utils.TokenUtils;
import com.example.utils.UserCF;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

/**
* @author 有宇
* @description 针对表【goods(商品信息表)】的数据库操作Service实现
* @createDate 2024-03-30 20:14:10
*/
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods>
    implements GoodsService {
    @Resource
    private CollectMapper collectMapper;
    @Resource
    private CartMapper cartMapper;
    @Resource
    private OrdersMapper ordersMapper;
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private GoodsMapper goodsMapper;
    @Resource
    private UserMapper userMapper;
    @Override
    public List<Goods> selectTop15() {
        return lambdaQuery()
                .orderByDesc(Goods::getCount)
                .last("limit 15")
                .list();
    }

    @Override
    public List<Goods> recommend() {
        Account currentUser = TokenUtils.getCurrentUser();
        if (ObjectUtil.isEmpty(currentUser)) {
            // 没有用户登录
            return new ArrayList<>();
        }
        // 用户的哪些行为可以认为他跟商品产生了关系？收藏、加入购物车、下单、评论
        // 1. 获取所有的收藏信息
        List<Collect> allCollects = collectMapper.selectList(null);
        // 2. 获取所有的购物车信息
        List<Cart> allCarts = cartMapper.selectList(null);
        // 3. 获取所有的订单信息
        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Orders::getStatus, "已完成");
        List<Orders> allOrders = ordersMapper.selectList(wrapper);
        // 4. 获取所有的评论信息
        List<Comment> allComments = commentMapper.selectList(null);
        // 5. 获取所有的用户信息
        List<User> allUsers = userMapper.selectList(null);
        // 6. 获取所有的商品信息
        List<Goods> allGoods = goodsMapper.selectList(null);

        // 定义一个存储每个商品和每个用户关系的List
        List<RelateDTO> data = new ArrayList<>();
        // 定义一个存储最后返回给前端的商品List
        List<Goods> result = new ArrayList<>();

        // 开始计算每个商品和每个用户之间的关系数据
        for (Goods goods : allGoods) {
            Integer goodsId = goods.getId();
            for (User user : allUsers) {
                Integer userId = user.getId();
                int index = 1;
                // 1. 判断该用户有没有收藏该商品，收藏的权重我们给 1
                Optional<Collect> collectOptional = allCollects.stream().filter(x -> x.getGoodsId().equals(goodsId) && x.getUserId().equals(userId)).findFirst();
                if (collectOptional.isPresent()) {
                    index += 1;
                }
                // 2. 判断该用户有没有给该商品加入购物车，加入购物车的权重我们给 2
                Optional<Cart> cartOptional = allCarts.stream().filter(x -> x.getGoodsId().equals(goodsId) && x.getUserId().equals(userId)).findFirst();
                if (cartOptional.isPresent()) {
                    index += 2;
                }
                // 3. 判断该用户有没有对该商品下过单（已完成的订单），订单的权重我们给 3
                Optional<Orders> ordersOptional = allOrders.stream().filter(x -> x.getGoodsId().equals(goodsId) && x.getUserId().equals(userId)).findFirst();
                if (ordersOptional.isPresent()) {
                    index += 3;
                }
                // 4. 判断该用户有没有对该商品评论过，评论的权重我们给 2
                Optional<Comment> commentOptional = allComments.stream().filter(x -> x.getGoodsId().equals(goodsId) && x.getUserId().equals(userId)).findFirst();
                if (commentOptional.isPresent()) {
                    index += 2;
                }
                if (index > 1) {
                    RelateDTO relateDTO = new RelateDTO(userId, goodsId, index);
                    data.add(relateDTO);
                }
            }
        }

        // 数据准备结束后，就把这些数据一起喂给这个推荐算法
        List<Integer> goodsIds = UserCF.recommend(currentUser.getId(), data);
        // 把商品id转换成商品
        List<Goods> recommendResult = goodsIds.stream().map(goodsId -> allGoods.stream()
                .filter(x -> x.getId().equals(goodsId)).findFirst().orElse(null))
                .limit(10).collect(Collectors.toList());

                if (CollectionUtil.isEmpty(recommendResult)) {
                    // 随机给它推荐10个
                    return getRandomGoods(10);
                }
                if (recommendResult.size() < 10) {
                    int num = 10 - recommendResult.size();
                    List<Goods> list = getRandomGoods(num);
                    result.addAll(list);
                }
        return recommendResult;
    }

    private List<Goods> getRandomGoods(int num) {
        List<Goods> list = new ArrayList<>(num);
        List<Goods> goods = goodsMapper.selectList(null);
        for (int i = 0; i < num; i++) {
            int index = new Random().nextInt(goods.size());
            list.add(goods.get(index));
        }
        return list;
    }
    }





