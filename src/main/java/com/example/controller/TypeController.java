package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.Result;
import com.example.entity.Typee;
import com.example.service.TypeService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/type")
public class TypeController {

    @Resource
    private TypeService typeService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Typee typee) {
        typeService.save(typee);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        typeService.removeById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        typeService.removeByIds(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody Typee typee) {
        typeService.updateById(typee);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Typee typee = typeService.getById(id);
        return Result.success(typee);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Typee typee) {
        QueryWrapper<Typee> queryWrapper = new QueryWrapper<>(typee);
        List<Typee> list = typeService.list(queryWrapper);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Typee typee,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {

        Page<Typee> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Typee> lqw = new LambdaQueryWrapper<>();

// 添加条件判断，如果查询条件不为空，则进行模糊查询
        if (StringUtils.isNotBlank(typee.getName())) {
            lqw.like(Typee::getName, typee.getName());
        }
        if (StringUtils.isNotBlank(typee.getDescription())) {
            lqw.or().like(Typee::getDescription, typee.getDescription());
        }
        IPage<Typee> resultpage = typeService.page(page,lqw);
        return Result.success(resultpage);
    }

}
