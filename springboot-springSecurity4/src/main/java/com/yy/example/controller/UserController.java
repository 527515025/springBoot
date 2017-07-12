package com.yy.example.controller;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping(value = "/users")
@RestController
public class UserController {


	@RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Object list(HttpServletRequest request) {
		return "Get all User";
    }

	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object detail(@PathVariable Integer id) {
		return "Get a user";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Object create(HttpServletRequest request) {
		return "POST a user";
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public Object update(HttpServletRequest request) {
		return "PUT a user";
    }

}