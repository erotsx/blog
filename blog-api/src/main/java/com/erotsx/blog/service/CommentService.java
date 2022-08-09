package com.erotsx.blog.service;

import com.erotsx.blog.entity.Comment;
import com.erotsx.blog.vo.CommentVo;
import com.erotsx.blog.vo.PageVo;
import com.erotsx.blog.vo.ReplyVo;

/**
 * @author erotsx
 */
public interface CommentService {
    /**
     * 发表评论
     *
     * @param comment 评论
     */
    void publish(Comment comment);

    /**
     * 删除评论
     *
     * @param id 评论id
     */
    void delete(String id);

    /**
     * 评论点赞
     *
     * @param id 评论id
     */
    void like(String id);

    /**
     * 根据文章id分页查询评论
     *
     * @param id       文章id
     * @param page     page
     * @param pageSize pageSize
     * @return 评论
     */
    PageVo<CommentVo> list(String id, int page, int pageSize);

    /**
     * 根据父评论id分页获取评论
     *
     * @param id       父评论id
     * @param page     page
     * @param pageSize pageSize
     * @return 评论
     */
    PageVo<ReplyVo> listReplies(String id, int page, int pageSize);
}
