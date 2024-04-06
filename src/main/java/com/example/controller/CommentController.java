package com.example.controller;

import cn.hutool.core.util.ObjectUtil;
import com.auth0.jwt.JWT;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.Constants;
import com.example.common.Result;
import com.example.common.enums.RoleEnum;
import com.example.entity.Comment;
import com.example.service.CommentService;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Resource
    private CommentService commentService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Comment comment) {
        commentService.save(comment);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        commentService.removeById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        commentService.removeByIds(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody Comment comment) {
        commentService.updateById(comment);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Comment comment = commentService.getById(id);
        return Result.success(comment);
    }

    @GetMapping("/selectByGoodsId")
    public Result selectByGoodsId(@RequestParam Integer id) {
        LambdaQueryWrapper<Comment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Comment::getGoodsId, id);
        List<Comment> list =  commentService.list(lambdaQueryWrapper);
        return Result.success(list);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Comment comment ) {
        LambdaQueryWrapper<Comment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        List<Comment> list = commentService.list(lambdaQueryWrapper);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Comment comment,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader(Constants.TOKEN);
        if (ObjectUtil.isNotEmpty(token)) {
            String userRole = JWT.decode(token).getAudience().get(0);
            String userId = userRole.split("-")[0];  // 获取用户id
            String role = userRole.split("-")[1];    // 获取角色
            System.out.println("--------------------------这是角色："+userRole);
            if ("2-BUSINESS".equals(userRole)) {

                return Result.success(commentService.selectPage1(pageNum, pageSize));
            }
            if (RoleEnum.BUSINESS.name().equals(userRole)) {


                return Result.success(commentService.selectPage2(comment.getBusinessId(), pageNum, pageSize));
            }

        }

//        if (RoleEnum.USER.name().equals(currentUser.getRole())) {
//
//        }
        return Result.success(commentService.selectPage3(comment.getGoodsId(), pageNum, pageSize));

    }

}
