package com.example.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.common.Constants;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Business;
import com.example.service.AdminService;
import com.example.service.BusinessService;
import com.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Token工具类
 */
@Component
public class TokenUtils {

    private static final Logger log = LoggerFactory.getLogger(TokenUtils.class);

    private static AdminService staticAdminService;
    private static BusinessService staticBusinessService;
    private static UserService staticUserService;

    @Resource
    AdminService adminService;
    @Resource
    BusinessService businessService;
    @Resource
    UserService userService;

    @PostConstruct
    public void setAdminService() {
        staticAdminService = adminService;
    }
    @PostConstruct
    public void setBusinessService() { staticBusinessService = businessService; }
    @PostConstruct
    public void setUserService() { staticUserService = userService; }

    /**
     * 生成token
     */
    public static String createToken(String data, String sign) {
        return JWT.create().withAudience(data) // 将 userId-role 保存到 token 里面,作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2)) // 2小时后token过期
                .sign(Algorithm.HMAC256(sign)); // 以 password 作为 token 的密钥
    }

    /**
     * 获取当前登录的用户信息
     */
    public static Account getCurrentUser() {
        Account account = null; // 初始化 account 变量
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader(Constants.TOKEN);
            if (ObjectUtil.isNotEmpty(token)) {
                String userRole = JWT.decode(token).getAudience().get(0);
                String userId = userRole.split("-")[0];  // 获取用户id
                String role = userRole.split("-")[1];    // 获取角色
                System.out.println(userId+"-----------------------------"+role);
                if (RoleEnum.ADMIN.name().equals(role)) {
                    return staticAdminService.selectById(Integer.valueOf(userId));
                }
                if (RoleEnum.BUSINESS.name().equals(role)) {
                    account = new Account(); // 创建一个新的 account 对象
                    BeanUtil.copyProperties(staticBusinessService.getById(Integer.valueOf(userId)), account);
                    account.setRole(RoleEnum.BUSINESS.name());
                    return account ;
                }
                if (RoleEnum.USER.name().equals(role)) {
                    BeanUtils.copyProperties(staticUserService.getById(Integer.valueOf(userId)), account);
                    account.setRole(RoleEnum.USER.name());
                    return account;
                }

            }
        } catch (Exception e) {
            log.error("获取当前用户信息出错", e);
        }
        return new Account();  // 返回空的账号对象
    }
}

