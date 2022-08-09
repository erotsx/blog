package com.erotsx.blog.controller;

import com.erotsx.blog.common.api.Result;
import com.erotsx.blog.entity.Comment;
import com.erotsx.blog.service.CommentService;
import com.erotsx.blog.vo.CommentVo;
import com.erotsx.blog.vo.PageVo;
import com.erotsx.blog.vo.ReplyVo;
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

    /**
     * 评论点赞
     *
     * @param id 评论id
     * @return String
     */
    @PostMapping("like/{id}")
    public Result<?> like(@PathVariable("id") String id) {
        commentService.like(id);
        return Result.success(null, "点赞成功");
    }

    /**
     * 根据文章id分页查询评论
     *
     * @param id       文章id
     * @param page     page
     * @param pageSize pageSize
     * @return 评论
     */
    @GetMapping("list")
    public Result<PageVo<CommentVo>> listByArticleId(@RequestParam String id,
                                                     @RequestParam(required = false, defaultValue = "1") int page,
                                                     @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return Result.success(commentService.list(id, page, pageSize));
    }

    /**
     * 根据父评论id分页获取评论
     *
     * @param id       父评论id
     * @param page     page
     * @param pageSize pageSize
     * @return 评论
     */
    @GetMapping("reply")
    public Result<PageVo<ReplyVo>> listReplies(@RequestParam String id,
                                               @RequestParam(required = false, defaultValue = "1") int page,
                                               @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return Result.success(commentService.listReplies(id, page, pageSize));
    }
}
