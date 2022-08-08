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
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentRepository commentRepository;

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
            sysUserInfo.setEmail(comment.getEmail());
            sysUserInfoMapper.insert(sysUserInfo);
        }
        comment.setUserId(String.valueOf(sysUser.getId()));
        comment.setCreateDate(DateUtil.date());
        commentRepository.save(comment);
    }

    /**
     * 删除评论
     *
     * @param id 评论id
     */
    @Override
    public void delete(String id) {

    }
}
