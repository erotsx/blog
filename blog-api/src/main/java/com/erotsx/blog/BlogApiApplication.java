package com.erotsx.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@MapperScan("com.erotsx.blog.dao")
public class BlogApiApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(BlogApiApplication.class, args);
        System.out.println("11");
    }

}
