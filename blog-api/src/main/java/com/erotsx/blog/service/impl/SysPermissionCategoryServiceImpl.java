package com.erotsx.blog.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.erotsx.blog.common.exception.Asserts;
import com.erotsx.blog.dao.SysPermissionCategoryMapper;
import com.erotsx.blog.dao.SysPermissionMapper;
import com.erotsx.blog.entity.SysPermission;
import com.erotsx.blog.entity.SysPermissionCategory;
import com.erotsx.blog.service.SysPermissionCategoryService;
import com.erotsx.blog.vo.SysPermissionCategoryVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class SysPermissionCategoryServiceImpl implements SysPermissionCategoryService {

    @Resource
    private SysPermissionCategoryMapper sysPermissionCategoryMapper;

    @Resource
    private SysPermissionMapper sysPermissionMapper;

    /**
     * 添加后台权限分类
     *
     * @param sysPermissionCategory SysPermissionCategory
     */
    @Override
    public void create(SysPermissionCategory sysPermissionCategory) {
        sysPermissionCategoryMapper.insert(sysPermissionCategory);
    }

    /**
     * 修改后台权限分类
     *
     * @param sysPermissionCategory SysPermissionCategory
     */
    @Override
    public void update(SysPermissionCategory sysPermissionCategory) {
        sysPermissionCategoryMapper.updateById(sysPermissionCategory);
    }

    /**
     * 根据Id删除后台权限分类
     *
     * @param id Id
     */
    @Override
    public void delete(Long id) {
        LambdaQueryWrapper<SysPermission> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysPermission::getCategoryId, id);
        if (CollUtil.isNotEmpty(sysPermissionMapper.selectList(queryWrapper))) {
            Asserts.fail("目录下含有权限，禁止删除");
        }
        sysPermissionCategoryMapper.deleteById(id);
    }

    /**
     * 获取所有权限目录及其权限列表
     *
     * @return 所有权限目录及其权限列表
     */
    @Override
    public List<SysPermissionCategoryVo> listAll() {
        List<SysPermissionCategoryVo> list = new ArrayList<>();
        List<SysPermissionCategory> categoryList = sysPermissionCategoryMapper.selectList(new LambdaQueryWrapper<>());
        for (SysPermissionCategory category : categoryList) {
            SysPermissionCategoryVo sysPermissionCategoryVo = new SysPermissionCategoryVo();
            BeanUtils.copyProperties(category, sysPermissionCategoryVo);
            sysPermissionCategoryVo.setChildren(getPermissionList(category.getId(), null));
            list.add(sysPermissionCategoryVo);
        }
        return list;
    }

    /**
     * 根据关键词搜索权限目录及其权限列表
     *
     * @param keyword 名称关键词
     * @return 权限目录及其权限列表
     */
    @Override
    public List<SysPermissionCategoryVo> search(String keyword) {
        if (StringUtils.isBlank(keyword)) {
            return listAll();
        }
        List<SysPermissionCategoryVo> list = new ArrayList<>();
        List<SysPermissionCategory> categoryList = sysPermissionCategoryMapper.selectList(new LambdaQueryWrapper<>());
        for (SysPermissionCategory category : categoryList) {
            SysPermissionCategoryVo sysPermissionCategoryVo = new SysPermissionCategoryVo();
            BeanUtils.copyProperties(category, sysPermissionCategoryVo);
            if (!getPermissionList(category.getId(), keyword).isEmpty()) {
                sysPermissionCategoryVo.setChildren(getPermissionList(category.getId(), keyword));
                list.add(sysPermissionCategoryVo);
            }
        }
        return list;
    }

    /**
     * 根据权限目录id获取权限列表
     *
     * @param id 权限目录id
     * @return 权限列表
     */
    private List<SysPermission> getPermissionList(Long id, String keyword) {
        LambdaQueryWrapper<SysPermission> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysPermission::getCategoryId, id);
        if (!StringUtils.isBlank(keyword)) {
            queryWrapper.like(SysPermission::getName, keyword);
        }
        return sysPermissionMapper.selectList(queryWrapper);
    }
}
