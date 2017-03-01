package com.us.example.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by yangyibo on 17/1/18.
 */
@Controller
@RequestMapping("/users")
public class HomeController {

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String getUsers() {
        return "getUsers";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String save() {
        return "saveUser";
    }


    @Secured("ROLE_ADMIN")
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public String update() {
        return "updateUser";
    }


    @Secured("ROLE_ADMIN")
    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    public String delete() {
        return "deleteUser";
    }


}

