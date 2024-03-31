package com.example.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.Goods;

import java.util.List;

/**
* @author 有宇
* @description 针对表【goods(商品信息表)】的数据库操作Service
* @createDate 2024-03-30 20:14:10
*/
public interface GoodsService extends IService<Goods> {

    List<Goods> selectTop15();
}
