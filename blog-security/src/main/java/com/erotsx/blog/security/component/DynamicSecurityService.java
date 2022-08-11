package com.erotsx.blog.security.component;

import org.springframework.security.access.ConfigAttribute;

import java.util.Map;

public interface DynamicSecurityService {

    Map<String, ConfigAttribute> loadDataSource();
}
