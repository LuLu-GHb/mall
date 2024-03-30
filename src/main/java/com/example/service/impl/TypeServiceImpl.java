package com.example.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Typee;
import com.example.mapper.TypeMapper;
import com.example.service.TypeService;
import org.springframework.stereotype.Service;

/**
* @author 有宇
* @description 针对表【type(商品分类表)】的数据库操作Service实现
* @createDate 2024-03-30 00:23:30
*/
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Typee>
    implements TypeService {

}




