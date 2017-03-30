package com.us.example.controller;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.us.example.service.UserService;
import com.us.example.util.CommonUtil;

/**
 *
 * @ClassName UserController
 * @author abel
 * @date 2016年11月10日
 */
@Controller
@RequestMapping(value = "/users")
public class UserController {
    private static final Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private UserService userService;


    /***
     * api :localhost:8099/users?id=99
     *  http://localhost:8099/users?limit=2&page=2
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResponseEntity<Object> list(HttpServletRequest request) {
        Map<String, Object> map = CommonUtil.getParameterMap(request);
        return new ResponseEntity<>(userService.getList(map), HttpStatus.OK);
    }


}