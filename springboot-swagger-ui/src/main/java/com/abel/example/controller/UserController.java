package com.abel.example.controller;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import com.abel.example.bean.User;
import io.swagger.annotations.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import com.abel.example.service.UserService;
import com.abel.example.util.CommonUtil;


@Controller
@RequestMapping(value = "/users")
@Api(value = "用户的增删改查")
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * 查询所有的用户
     * api :localhost:8099/users
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取用户列表，目前没有分页")
    public ResponseEntity<Object> findAll() {
        return new ResponseEntity<>(userService.listUsers(), HttpStatus.OK);
    }

    /**
     * 通过id 查找用户
     * api :localhost:8099/users/1
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "通过id获取用户信息", notes="返回用户信息")
    public ResponseEntity<Object> getUserById(@PathVariable Integer id) {
        return new ResponseEntity<>(userService.getUserById(Long.valueOf(id)), HttpStatus.OK);
    }


    /**
     * 通过spring data jpa 调用方法
     * api :localhost:8099/users/byname?username=xxx
     * 通过用户名查找用户
     * @param request
     * @return
     */
    @RequestMapping(value = "/byname", method = RequestMethod.GET)
    @ResponseBody
    @ApiImplicitParam(paramType = "query",name= "username" ,value = "用户名",dataType = "string")
    @ApiOperation(value = "通过用户名获取用户信息", notes="返回用户信息")
    public ResponseEntity<Object> getUserByUserName(HttpServletRequest request) {
        Map<String, Object> map = CommonUtil.getParameterMap(request);
        String username = (String) map.get("username");
        return new ResponseEntity<>(userService.getUserByUserName(username), HttpStatus.OK);
    }

    /**
     * 通过spring data jpa 调用方法
     * api :localhost:8099/users/byUserNameContain?username=xxx
     * 通过用户名模糊查询
     * @param request
     * @return
     */
    @RequestMapping(value = "/byUserNameContain", method = RequestMethod.GET)
    @ResponseBody
    @ApiImplicitParam(paramType = "query",name= "username" ,value = "用户名",dataType = "string")
    @ApiOperation(value = "通过用户名模糊搜索用户信息", notes="返回用户信息")
    public ResponseEntity<Object> getUsers(HttpServletRequest request) {
        Map<String, Object> map = CommonUtil.getParameterMap(request);
        String username = (String) map.get("username");
        return new ResponseEntity<>(userService.getByUsernameContaining(username), HttpStatus.OK);
    }


    /**
     * 添加用户啊
     * api :localhost:8099/users
     *
     * @param user
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    @ApiModelProperty(value="user",notes = "用户信息的json串")
    @ApiOperation(value = "新增用户", notes="返回新增的用户信息")
    public ResponseEntity<Object> saveUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.OK);
    }

    /**
     * 修改用户信息
     * api :localhost:8099/users
     * @param user
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    @ApiModelProperty(value="user",notes = "修改后用户信息的json串")
    @ApiOperation(value = "新增用户", notes="返回新增的用户信息")
    public ResponseEntity<Object> updateUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.updateUser(user), HttpStatus.OK);
    }

    /**
     * 通过ID删除用户
     * api :localhost:8099/users/2
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    @ApiOperation(value = "通过id删除用户信息", notes="返回删除状态1 成功 0 失败")
    public ResponseEntity<Object> deleteUser(@PathVariable Integer id) {
        return new ResponseEntity<>(userService.removeUser(id.longValue()), HttpStatus.OK);
    }
}