package com.lsc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @Description: Security配置类
 * @Author: lisc
 * @date: 2022/7/22
 */
@Configuration
public class AtcrowdfundingSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 定义认证规则
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      //  super.configure(auth);
    }

    /**
     * 定义http访问规则, 授权
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      //  super.configure(http);
    }
}
