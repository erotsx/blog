package com.erotsx.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.erotsx.blog.bo.AdminUserDetails;
import com.erotsx.blog.common.exception.Asserts;
import com.erotsx.blog.dao.SysUserMapper;
import com.erotsx.blog.entity.SysPermission;
import com.erotsx.blog.entity.SysRole;
import com.erotsx.blog.entity.SysUser;
import com.erotsx.blog.security.utils.JWTUtils;
import com.erotsx.blog.service.SysAdminService;
import com.erotsx.blog.service.SysPermissionService;
import com.erotsx.blog.service.SysUserService;
import com.erotsx.blog.vo.AdminParams;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@Transactional
public class SysAdminServiceImpl implements SysAdminService {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private SysPermissionService sysPermissionService;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public Map<String, String> login(AdminParams adminParams) {
        UserDetails userDetails = loadUserByUsername(adminParams.getAccount());
        if (!passwordEncoder.matches(adminParams.getPassword(), userDetails.getPassword())) {
            Asserts.fail("密码错误");
        }
        if (!userDetails.isEnabled()) {
            Asserts.fail("账号已被禁用");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        String token = JWTUtils.createToken(userDetails.getUsername());
        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(userDetails), 1, TimeUnit.DAYS);
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        return map;
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete("TOKEN_" + token);
    }

    @Override
    public Map<String, String> register(AdminParams adminParams) {
        String account = adminParams.getAccount();
        String password = adminParams.getPassword();
        String nickname = adminParams.getNickname();
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password) || StringUtils.isBlank(nickname)) {
            Asserts.fail("参数存在空值");
        }
        SysUser sysUser = sysUserService.findSysUserByAccount(account);
        if (sysUser != null) {
            Asserts.fail("账户已存在");
        }
        sysUser = new SysUser();
        String encodePassword = passwordEncoder.encode(password);
        sysUser.setAccount(account);
//        sysUser.setNickname(nickname);
        sysUser.setPassword(encodePassword);
        sysUser.setStatus("1");
        sysUserService.insert(sysUser);
        String token = JWTUtils.createToken(sysUser.getAccount());
        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        return map;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount, username);
        SysUser sysUser = sysUserMapper.selectOne(queryWrapper);
        if (sysUser == null) {
            Asserts.fail("用户不存在");
        }
        List<SysPermission> permissionList = getPermissionList(sysUser.getId());
        return new AdminUserDetails(sysUser, permissionList);
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
     * 获取用户角色
     *
     * @param id 用户id
     * @return 角色列表
     */
    @Override
    public List<SysRole> listRoles(Long id) {
        return sysUserMapper.listRoles(id);
    }

    private List<SysPermission> getPermissionList(Long id) {
        return sysPermissionService.getPermissionList(id);
    }
}
