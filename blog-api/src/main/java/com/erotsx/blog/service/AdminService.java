package com.erotsx.blog.service;

import com.erotsx.blog.vo.AdminParams;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Map;

public interface AdminService {
    Map<String, String> login(AdminParams adminParams);

    void logout(String token);

    Map<String,String> register(AdminParams adminParams);

    UserDetails loadUserByUsername(String username);
}
