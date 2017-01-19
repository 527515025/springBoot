package com.us.example.dao;

import com.us.example.config.MyBatisRepository;
import com.us.example.domain.SysUser;


@MyBatisRepository
public interface UserDao {
    public SysUser findByUserName(String username);
}
