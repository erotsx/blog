package com.erotsx.blog.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.erotsx.blog.bo.AdminUserDetails;
import com.erotsx.blog.dao.SysUserInfoMapper;
import com.erotsx.blog.dao.SysUserMapper;
import com.erotsx.blog.entity.SysRole;
import com.erotsx.blog.entity.SysUser;
import com.erotsx.blog.entity.SysUserInfo;
import com.erotsx.blog.service.SysRoleService;
import com.erotsx.blog.service.SysUserInfoService;
import com.erotsx.blog.service.SysUserService;
import com.erotsx.blog.service.ThreadService;
import com.erotsx.blog.utils.ImgBedUtils;
import com.erotsx.blog.vo.PageVo;
import com.erotsx.blog.vo.SysUserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SysUserInfoService sysUserInfoService;

    @Resource
    private SysUserInfoMapper sysUserInfoMapper;

    @Resource
    private ThreadService threadService;

    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public SysUser findSysUserById(Long authorId) {
        return sysUserMapper.selectById(authorId);
    }


    /**
     * 获取当前用户信息
     *
     * @return 返回用户信息
     */
    @Override
    public SysUserVo getUserInfo() {

        SysUser sysUser = getCurrentUser();
        SysUserInfo sysUserInfo = sysUserInfoService.findSysUserInfoById(sysUser.getId());
        SysUserVo sysUserVo = new SysUserVo();
        BeanUtils.copyProperties(sysUser, sysUserVo);
        BeanUtils.copyProperties(sysUserInfo, sysUserVo);
        return sysUserVo;
    }

    @Override
    public SysUser findSysUserByAccount(String account) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount, account);
        return sysUserMapper.selectOne(queryWrapper);
    }

    @Override
    public void insert(SysUser sysUser) {
        sysUserMapper.insert(sysUser);
    }

    @Override
    public String updateAvatar(MultipartFile file) throws IOException {
        JSONObject data = ImgBedUtils.upload(file);
        SysUser sysUser = getCurrentUser();
        SysUserInfo sysUserInfo = sysUserInfoService.findSysUserInfoById(sysUser.getId());
        String avatar = data.getString("url");
        String delete = data.getString("delete");
        if (!StringUtils.isBlank(sysUserInfo.getAvatarDelete())) {
            threadService.deleteAvatar(sysUserInfo.getAvatarDelete());
        }
        sysUserInfo.setAvatar(avatar);
        sysUserInfo.setAvatarDelete(delete);
        sysUserInfoService.updateById(sysUserInfo);
        return avatar;
    }

    @Override
    public SysUser getCurrentUser() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();
        AdminUserDetails adminUserDetails = (AdminUserDetails) auth.getPrincipal();
        return adminUserDetails.getSysUser();
    }

    @Override
    public SysUserVo getBloggerInfo() {
        SysRole sysRole = sysRoleService.getRoleByName("blogger");
        SysUser sysUser = sysUserMapper.getBlogger(sysRole.getId());
        SysUserInfo sysUserInfo = sysUserInfoService.findSysUserInfoById(sysUser.getId());
        SysUserVo sysUserVo = new SysUserVo();
        BeanUtils.copyProperties(sysUser, sysUserVo);
        BeanUtils.copyProperties(sysUserInfo, sysUserVo);
        return sysUserVo;
    }

    /**
     * 根据关键词分页搜索用户信息
     *
     * @param keyword  昵称关键词
     * @param page     page
     * @param pageSize pageSize
     * @return 用户信息列表
     */
    @Override
    public PageVo<SysUserVo> search(String keyword, int page, int pageSize) {
        LambdaQueryWrapper<SysUserInfo> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isBlank(keyword)) {
            queryWrapper.like(SysUserInfo::getNickname, keyword);
        }
        Page<SysUserInfo> userInfoPage = sysUserInfoMapper.selectPage(new Page<>(page, pageSize), queryWrapper);
        List<SysUserVo> list = new ArrayList<>();
        for (SysUserInfo sysUserInfo : userInfoPage.getRecords()) {
            SysUserVo sysUserVo = new SysUserVo();
            BeanUtils.copyProperties(sysUserInfo, sysUserVo);
            BeanUtils.copyProperties(sysUserMapper.selectById(sysUserInfo.getUserId()), sysUserVo);
            sysUserVo.setRoleList(listRoles(sysUserInfo.getUserId()));
            list.add(sysUserVo);
        }
        return new PageVo<>(list, userInfoPage.getTotal());
    }

    /**
     * 获取用户角色
     *
     * @param id 用户id
     * @return 角色列表
     */
    @Override
    public List<SysRole> listRoles(Long id) {
        return sysUserMapper.listRoles(id);
    }

    /**
     * 修改用户角色
     *
     * @param userId     用户id
     * @param roleIdList 角色id列表
     */
    @Override
    public void updateRole(Long userId, List<Long> roleIdList) {
        sysUserMapper.deleteRoleRelation(userId);
        sysUserMapper.insertRoleRelation(userId, roleIdList);
    }

    /**
     * 修改用户
     *
     * @param sysUser user
     */
    @Override
    public void update(SysUser sysUser) {
        sysUserMapper.updateById(sysUser);
    }

    /**
     * 修改用户信息
     *
     * @param id          用户id
     * @param sysUserInfo 用户信息
     */
    @Override
    public void updateInfo(Long id, SysUserInfo sysUserInfo) {
        LambdaUpdateWrapper<SysUserInfo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SysUserInfo::getUserId, id);
        sysUserInfoMapper.update(sysUserInfo, updateWrapper);
    }

}
