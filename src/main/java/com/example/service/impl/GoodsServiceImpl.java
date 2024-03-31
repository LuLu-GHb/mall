package com.example.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Goods;
import com.example.mapper.GoodsMapper;
import com.example.service.GoodsService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 有宇
* @description 针对表【goods(商品信息表)】的数据库操作Service实现
* @createDate 2024-03-30 20:14:10
*/
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods>
    implements GoodsService {

    @Override
    public List<Goods> selectTop15() {
        return lambdaQuery()
                .orderByDesc(Goods::getCount)
                .last("limit 15")
                .list();
    }
}




