package com.erotsx.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.erotsx.blog.dao.SysPermissionCategoryMapper;
import com.erotsx.blog.dao.SysPermissionMapper;
import com.erotsx.blog.entity.SysPermission;
import com.erotsx.blog.entity.SysPermissionCategory;
import com.erotsx.blog.service.SysPermissionCategoryService;
import com.erotsx.blog.vo.SysPermissionCategoryVo;
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
            sysPermissionCategoryVo.setChildren(getPermissionList(category.getId()));
            list.add(sysPermissionCategoryVo);
        }
        return list;
    }

    /**
     * 根据权限目录id获取权限列表
     *
     * @param id 权限目录id
     * @return 权限列表
     */
    private List<SysPermission> getPermissionList(Long id) {
        LambdaQueryWrapper<SysPermission> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysPermission::getCategoryId, id);
        return sysPermissionMapper.selectList(queryWrapper);
    }
}
