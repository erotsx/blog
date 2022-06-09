package com.erotsx.blog.service;

import com.erotsx.blog.vo.AdminParams;
import com.erotsx.blog.vo.Result;

public interface AdminService {
    Result login(AdminParams adminParams);

    Result logout(String token);

    Result register(AdminParams adminParams);
}
