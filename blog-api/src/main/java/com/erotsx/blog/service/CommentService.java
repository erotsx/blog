package com.erotsx.blog.service;

import com.erotsx.blog.entity.Comment;
import com.erotsx.blog.vo.CommentVo;

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
}
