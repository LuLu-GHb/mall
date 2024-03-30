package com.example.service.impl;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.common.enums.StatusEnum;
import com.example.entity.Account;
import com.example.entity.Business;
import com.example.exception.CustomException;
import com.example.mapper.BusinessMapper;
import com.example.service.BusinessService;
import com.example.utils.TokenUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author 有宇
* @description 针对表【business(商家信息表)】的数据库操作Service实现
* @createDate 2024-03-30 13:43:52
*/
@Service
public class BusinessServiceImpl extends ServiceImpl<BusinessMapper, Business>
    implements BusinessService {
    @Resource
    private BusinessMapper businessMapper;
    @Resource
    private BusinessService businessService;
    /**
     * 登录
     */
    public Account login(Account account) {
        LambdaQueryWrapper<Business> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Business::getUsername, account.getUsername());
        Business dbBusiness = businessMapper.selectOne(queryWrapper);
        if (ObjectUtil.isNull(dbBusiness)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        if (!account.getPassword().equals(dbBusiness.getPassword())) {
            throw new CustomException(ResultCodeEnum.USER_ACCOUNT_ERROR);
        }
        // 生成token
        String tokenData = dbBusiness.getId() + "-" + RoleEnum.BUSINESS.name();
        String token = TokenUtils.createToken(tokenData, dbBusiness.getPassword());
        Account dbAccount = new Account();
        BeanUtils.copyProperties(dbBusiness, dbAccount);
        dbAccount.setToken(token);
        return dbAccount;
    }
    /*
    * 注册
    */
    public void register(Account account) {
        Business business = new Business();
        BeanUtils.copyProperties(account, business);
        business.setStatus(StatusEnum.CHECKING.status);
        businessService.save(business);

    }

}




