package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.Result;
import com.example.common.enums.RoleEnum;
import com.example.entity.Business;
import com.example.service.BusinessService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/business")
public class BusinessController {

    @Resource
    private BusinessService businessService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Business business) {
        business.setRole(RoleEnum.BUSINESS.name());
        businessService.save(business);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        businessService.removeById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        businessService.removeByIds(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody Business business) {
        businessService.updateById(business);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Business business = businessService.getById(id);
        return Result.success(business);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Business business ) {
        QueryWrapper<Business> queryWrapper = new QueryWrapper<>(business);
        List<Business> list = businessService.list(queryWrapper);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Business business,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Business> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Business> lqw = new LambdaQueryWrapper<>();
        // 添加条件判断，如果查询条件不为空，则进行模糊查询
        if (StringUtils.isNotBlank(business.getUsername())) {
            lqw.like(Business::getUsername, business.getUsername());
        }

        IPage<Business> resultpage = businessService.page(page,lqw);
        return Result.success(resultpage);
    }

}
