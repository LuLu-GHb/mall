package com.example.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.Account;
import com.example.entity.User;

/**
* @author 有宇
* @description 针对表【user(用户信息表)】的数据库操作Service
* @createDate 2024-03-30 15:43:13
*/
public interface UserService extends IService<User> {
    Account login(Account account) ;
    /**
     * 注册
     */
    void register(Account account) ;

}
