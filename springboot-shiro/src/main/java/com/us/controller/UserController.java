package com.us.controller;


import com.us.bean.User;
import com.us.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping(value = "/users")
@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	@RequestMapping(method = RequestMethod.GET)
    public List<User> list(HttpServletRequest request) {
		return userService.getByMap(null);
    }
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User detail(@PathVariable Integer id) {
		return userService.getById(id);
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public User create(@RequestBody User user) {
		return userService.create(user);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public User update(@RequestBody User user) {
		return userService.update(user);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public int delete(@PathVariable Integer id) {
		return userService.delete(id);
    }
    
}