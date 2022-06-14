package com.erotsx.blog.service;

import com.erotsx.blog.vo.AdminParams;


import java.util.Map;

public interface AdminService {
    Map<String, String> login(AdminParams adminParams);

    void logout(String token);

    Map<String,String> register(AdminParams adminParams);
}
