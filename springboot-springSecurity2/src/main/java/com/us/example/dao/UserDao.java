package com.us.example.dao;

import com.us.example.domain.SysUser;


public interface UserDao {
     SysUser findByUserName(String username);

     int create (SysUser sysUser);
}
