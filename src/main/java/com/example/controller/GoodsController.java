package com.example.controller;

import cn.hutool.core.util.ObjectUtil;
import com.auth0.jwt.JWT;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.Constants;
import com.example.common.Result;
import com.example.common.enums.RoleEnum;
import com.example.entity.Business;
import com.example.entity.Comment;
import com.example.entity.Goods;
import com.example.entity.Typee;
import com.example.entity.goods.GoodsDetail;
import com.example.service.BusinessService;
import com.example.service.GoodsService;

import com.example.service.TypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Resource
    private GoodsService goodsService;
    @Resource
    TypeService typeService;
    @Resource
    BusinessService businessService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Goods goods) {
        goodsService.save(goods);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        goodsService.removeById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        goodsService.removeByIds(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody Goods goods) {
        goodsService.updateById(goods);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById")
    public Result selectById(@RequestParam Integer id) {
        GoodsDetail goodsDetail = new GoodsDetail();
        Goods goods = goodsService.getById(id);
        Typee type = typeService.getById(goods.getTypeId());
        Business business = businessService.getById(goods.getBusinessId());
        BeanUtils.copyProperties(goods,goodsDetail);
        goodsDetail.setTypeName(type.getName());
        goodsDetail.setBusinessName(business.getName());
        return Result.success(goodsDetail);
    }

    @GetMapping("/selectByName")
    public Result selectByName(@RequestParam String name) {
        LambdaQueryWrapper<Goods> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(name)) {
            lambdaQueryWrapper.like(Goods::getName, name);
        }
        List<Goods> list = goodsService.list(lambdaQueryWrapper);

        return Result.success(list);
    }
    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Goods goods ) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>(goods);
        List<Goods> list = goodsService.list(queryWrapper);
        return Result.success(list);
    }

    /*
     * 商城根据类型查询
     */
    @GetMapping("/selectByTypeId")
    public Result selectByTypeId(@RequestParam Integer id) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type_id",id);
        List<Goods> list = goodsService.list(queryWrapper);
        return Result.success(list);
    }
    /*
    * 查询店铺所有商品*/
    @GetMapping("/selectByBusinessId")
    public Result selectByBusinessId(@RequestParam Integer id) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("business_id",id);
        List<Goods> list = goodsService.list(queryWrapper);
        return Result.success(list);
    }
    /*
    * 查询top15
    */
    @GetMapping("/selectTop15")
    public Result selectTop15() {
        List<Goods> list = goodsService.selectTop15();
        return Result.success(list);
    }
//todo,商品页面显示商家
    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Goods goods,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize, HttpServletRequest request) {
        String token = request.getHeader(Constants.TOKEN);
            String userRole = JWT.decode(token).getAudience().get(0);
            String userId = userRole.split("-")[0];  // 获取用户id
            String role = userRole.split("-")[1];    // 获取角色
            Page<Goods> page = new Page<>(pageNum, pageSize);
            LambdaQueryWrapper<Goods> lqw = new LambdaQueryWrapper<>();
            // 添加条件判断，如果查询条件不为空，则进行模糊查询
            if (StringUtils.isNotBlank(goods.getName())) {
                lqw.like(Goods::getName, goods.getName());
            }
            if (RoleEnum.BUSINESS.name().equals(role)) {
                lqw.eq(Goods::getBusinessId, userId);
            }

            IPage<Goods> resultpage = goodsService.page(page, lqw);
            return Result.success(resultpage);
        }


}
