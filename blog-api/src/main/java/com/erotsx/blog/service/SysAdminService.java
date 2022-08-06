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

}
