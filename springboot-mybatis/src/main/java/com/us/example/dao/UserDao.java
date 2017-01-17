package com.us.example.dao;

import java.util.List;
import java.util.Map;

import com.us.example.bean.User;
import com.us.example.config.MyBatisRepository;

@MyBatisRepository
public interface UserDao {
    public List<User> getList(Map<String,Object> map);
}
