package com.example.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Cart;
import com.example.mapper.CartMapper;
import com.example.service.CartService;
import org.springframework.stereotype.Service;

/**
* @author 有宇
* @description 针对表【cart(购物车表)】的数据库操作Service实现
* @createDate 2024-04-01 15:43:19
*/
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart>
    implements CartService {

}




