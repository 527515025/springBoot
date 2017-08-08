package com.us.example.controller;

import com.us.example.domain.Msg;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yangyibo on 17/1/18.
 */
@RestController
public class HomeController {

    @RequestMapping("/")
    public String index(Model model){
        Msg msg =  new Msg("测试标题","测试内容","欢迎来到HOME页面,您拥有 ROLE_HOME 权限");
        model.addAttribute("msg", msg);
        return "home";
    }


    @RequestMapping("/admin")
    public String hello(){
        return "hello admin";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String getList(){
        return "hello getList";
    }


    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String save(){
        return "hello save";
    }


    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public String update(){
        return "hello update";
    }
}
