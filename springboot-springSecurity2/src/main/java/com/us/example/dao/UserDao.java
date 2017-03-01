package com.us.example.dao;

import com.us.example.domain.SysUser;


public interface UserDao {
    public SysUser findByUserName(String username);
}
