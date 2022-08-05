package com.erotsx.blog.service;

import com.erotsx.blog.entity.SysRole;
import com.erotsx.blog.vo.AdminParams;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.List;
import java.util.Map;

public interface SysAdminService {
    Map<String, String> login(AdminParams adminParams);

    void logout(String token);

    Map<String,String> register(AdminParams adminParams);

    UserDetails loadUserByUsername(String username);

    /**
     * 修改用户角色
     *
     * @param userId     用户id
     * @param roleIdList 角色id列表
     */
    void updateRole(Long userId, List<Long> roleIdList);

    /**
     * 获取用户角色
     *
     * @param id 用户id
     * @return 角色列表
     */
    List<SysRole> listRoles(Long id);
}
