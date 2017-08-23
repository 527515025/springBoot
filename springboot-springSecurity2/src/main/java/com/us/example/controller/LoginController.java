package com.us.example.controller;

import com.us.example.domain.SysUser;
import com.us.example.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by yangyibo on 17/3/1.
 */
@Controller
public class LoginController {
    @Autowired
    private SessionRegistry sessionRegistry;
    /**
     * http://localhost:8080/login
     * http://localhost:8080/logout
     * @param loginedUser
     * @param logout
     * @return
     */
    @RequestMapping(value = "/login")
    @ResponseBody
    //用户名密码是用base64 加密 原文为 admin:admin 即 用户名:密码  内容是放在request.getHeader 的 "authorization" 中
    public Object login(@AuthenticationPrincipal SysUser loginedUser, @RequestParam(name = "logout", required = false) String logout,HttpServletRequest request) {
        if (logout != null) {
            return "logout";
        }
        if (loginedUser != null) {
            SessionUtil.dropPreviousUser(request,sessionRegistry,loginedUser);
            return loginedUser;
        }
        return null;
    }

//    此方法未用到
//    @RequestMapping(value="/logout", method = RequestMethod.GET)
//    @ResponseBody
//    public String logout (HttpServletRequest request, HttpServletResponse response) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null){
//            new SecurityContextLogoutHandler().logout(request, response, auth);
//        }
//        return "logout ok";
//    }
}
