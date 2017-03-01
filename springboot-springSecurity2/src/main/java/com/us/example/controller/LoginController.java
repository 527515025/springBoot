package com.us.example.controller;

import com.us.example.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by yangyibo on 17/3/1.
 */
@RestController
public class LoginController {
    @Autowired
    UserDao userDao;

    @RequestMapping(value = "/login")
    @ResponseBody
    //用户名密码是用base64 加密 原文为 admin:admin 即 用户名:密码  内容是放在request.getHeader 的 "authorization" 中
    public Object login(HttpServletRequest request, @RequestParam(name = "logout", required = false) String logout) {
        if (logout != null) {
            return null;
        }
        SecurityContext sc = (SecurityContext) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        User user = (User) sc.getAuthentication().getPrincipal();
        return  userDao.findByUserName(user.getUsername());
    }
}
