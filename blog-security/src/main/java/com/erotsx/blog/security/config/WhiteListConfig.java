package com.erotsx.blog.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Data
@ConfigurationProperties(prefix = "security")
public class WhiteListConfig {

    private List<String> whiteList = new ArrayList<>();
}
