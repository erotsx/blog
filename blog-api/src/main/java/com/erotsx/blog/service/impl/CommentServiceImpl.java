package com.erotsx.blog.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.erotsx.blog.dao.CommentRepository;
import com.erotsx.blog.dao.SysUserInfoMapper;
import com.erotsx.blog.dao.SysUserMapper;
import com.erotsx.blog.entity.Comment;
import com.erotsx.blog.entity.SysUser;
import com.erotsx.blog.entity.SysUserInfo;
import com.erotsx.blog.service.CommentService;
import com.erotsx.blog.vo.CommentVo;
import com.erotsx.blog.vo.PageVo;
import com.erotsx.blog.vo.ReplyVo;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentRepository commentRepository;

    @Resource
    private MongoTemplate mongoTemplate;

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SysUserInfoMapper sysUserInfoMapper;

    /**
     * 发表评论
     *
     * @param comment 评论
     */
    @Override
    public void publish(Comment comment) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount, comment.getEmail());
        SysUser sysUser = sysUserMapper.selectOne(queryWrapper);
        if (sysUser == null) {
            sysUser = new SysUser();
            sysUser.setAccount(comment.getEmail());
            sysUser.setStatus("0");
            sysUserMapper.insert(sysUser);
            SysUserInfo sysUserInfo = new SysUserInfo();
            sysUserInfo.setUserId(sysUser.getId());
            sysUserInfo.setAvatar("https://s3.bmp.ovh/imgs/2022/08/09/dc5018f0e1382db2.png");
            sysUserInfo.setEmail(comment.getEmail());
            sysUserInfoMapper.insert(sysUserInfo);
        }
        comment.setUserId(String.valueOf(sysUser.getId()));
        comment.setCreateDate(DateUtil.date());
        comment.setLikeCounts(0);
        comment.setReplyCounts(0);
        //TODO 使用线程池
        if (!"0".equals(comment.getParentId())) {
            Query query = Query.query(Criteria.where("_id").is(comment.getParentId()));
            Update update = new Update();
            update.inc("replyCounts");
            mongoTemplate.updateFirst(query, update, Comment.class);
        }
        commentRepository.save(comment);
    }

    /**
     * 删除评论
     *
     * @param id 评论id
     */
    @Override
    public void delete(String id) {
        Query query = Query.query(Criteria.where("_id").is(id));
        Comment comment = mongoTemplate.findOne(query, Comment.class);
        assert comment != null;
        if (!Objects.equals(comment.getParentId(), "0")) {
            Query query1 = Query.query(Criteria.where("_id").is(comment.getParentId()));
            Update update = new Update();
            update.inc("replyCounts", -1);
            mongoTemplate.updateFirst(query1, update, Comment.class);
        }

        commentRepository.deleteById(id);
    }

    /**
     * 评论点赞
     *
     * @param id 评论id
     */
    @Override
    public void like(String id) {
        Query query = Query.query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.inc("likeCounts");
        mongoTemplate.updateFirst(query, update, Comment.class);
    }

    /**
     * 根据文章id分页查询评论
     *
     * @param id       文章id
     * @param page     page
     * @param pageSize pageSize
     * @return 评论
     */
    @Override
    public PageVo<CommentVo> list(String id, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Order.desc("createDate")));
        Query query = new Query(Criteria.where("articleId").is(id).and("parentId").is("0"));
        long count = mongoTemplate.count(query, Comment.class);
        query.with(pageable);
        List<Comment> comments = mongoTemplate.find(query, Comment.class);
        List<CommentVo> commentVoList = comments.stream()
                .map(comment -> {
                    CommentVo commentVo = new CommentVo();
                    commentVo.setReplyVoList(listReplyVoList(comment.getId()));
                    LambdaQueryWrapper<SysUserInfo> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.eq(SysUserInfo::getUserId, comment.getUserId());
                    SysUserInfo sysUserInfo = sysUserInfoMapper.selectOne(queryWrapper);
                    commentVo.setAvatar(sysUserInfo.getAvatar());
                    BeanUtils.copyProperties(comment, commentVo);
                    return commentVo;
                })
                .collect(Collectors.toList());
        return new PageVo<>(commentVoList, count);
    }

    /**
     * 根据父评论id分页获取评论
     *
     * @param id       父评论id
     * @param page     page
     * @param pageSize pageSize
     * @return 评论
     */
    @Override
    public PageVo<ReplyVo> listReplies(String id, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Order.desc("createDate")));
        Query query = new Query(Criteria.where("parentId").is(id));
        long count = mongoTemplate.count(query, Comment.class);
        query.with(pageable);
        List<Comment> commentList = mongoTemplate.find(query, Comment.class);
        List<ReplyVo> replyVoList = commentList.stream()
                .map(this::getReplyVo)
                .collect(Collectors.toList());
        return new PageVo<>(replyVoList, count);
    }

    private List<ReplyVo> listReplyVoList(String parentId) {
        Query query = new Query(Criteria.where("parentId").is(parentId)).limit(3);
        List<Comment> commentList = mongoTemplate.find(query, Comment.class);
        return commentList.stream()
                .map(this::getReplyVo)
                .collect(Collectors.toList());
    }


    private ReplyVo getReplyVo(Comment comment) {
        ReplyVo replyVo = new ReplyVo();
        BeanUtils.copyProperties(comment, replyVo);
        LambdaQueryWrapper<SysUserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserInfo::getUserId, comment.getUserId());
        SysUserInfo sysUserInfo = sysUserInfoMapper.selectOne(queryWrapper);
        replyVo.setAvatar(sysUserInfo.getAvatar());
        return replyVo;
    }
}
