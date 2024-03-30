package com.example.service;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Business;
import com.example.exception.CustomException;
import com.example.utils.TokenUtils;

/**
* @author 有宇
* @description 针对表【business(商家信息表)】的数据库操作Service
* @createDate 2024-03-30 13:43:52
*/
public interface BusinessService extends IService<Business> {
    /**
     * 登录
     */
    Account login(Account account) ;
    /**
     * 注册
     */
    void register(Account account) ;

}
