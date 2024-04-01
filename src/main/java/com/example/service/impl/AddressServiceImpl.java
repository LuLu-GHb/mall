package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Address;
import com.example.mapper.AddressMapper;
import com.example.service.AddressService;
import org.springframework.stereotype.Service;

/**
* @author 有宇
* @description 针对表【address(地址信息表)】的数据库操作Service实现
* @createDate 2024-04-01 15:29:20
*/
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address>
    implements AddressService {

}




