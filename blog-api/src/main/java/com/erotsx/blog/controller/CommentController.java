package com.erotsx.blog.controller;

import com.erotsx.blog.common.api.Result;
import com.erotsx.blog.entity.Comment;
import com.erotsx.blog.service.CommentService;
import com.erotsx.blog.vo.CommentVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("comment")
public class CommentController {

    @Resource
    private CommentService commentService;

    /**
     * 发表评论
     *
     * @param comment 评论
     * @return String
     */
    @PostMapping("publish")
    public Result<?> publish(@RequestBody Comment comment) {
        commentService.publish(comment);
        return Result.success(null, "发表成功");
    }

    /**
     * 删除评论
     *
     * @param id 评论id
     * @return String
     */
    @DeleteMapping("delete/{id}")
    public Result<?> delete(@PathVariable("id") String id) {
        commentService.delete(id);
        return Result.success(null, "删除成功");
    }
}
