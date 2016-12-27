package com.us.example.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

/**
 * Created by yangyibo on 16/12/27.
 */
@RestController
public class helloController {

    // 访问 http://localhost:8080/login  账号 abel 密码 123
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    @ResponseBody
    public String viewHome() {
        return "hello SpringSecurity";
    }

}
