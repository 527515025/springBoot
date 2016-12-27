package com.us.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by yangyibo on 16/12/27.
 */
    @Configuration          // 配置文件
    @EnableWebSecurity      // 开启Security
    @EnableGlobalMethodSecurity(prePostEnabled = true)  //AOP
    public class SecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            //路由策略和访问权限的简单配置
            http
                    .formLogin()                        //启用默认登陆页面
                    .failureUrl("/login/error")         //登陆失败返回URL:/login?error
                    .defaultSuccessUrl("/home")         //登陆成功跳转URL
                    .permitAll();                       //登陆页面全部权限可访问

            super.configure(http);
        }
//
//    /**
//     * 配置内存用户
//     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
            .withUser("abel").password("123").roles("USER");
    }

    }

