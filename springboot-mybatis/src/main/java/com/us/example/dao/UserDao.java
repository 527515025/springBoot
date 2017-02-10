package com.us.example.dao;

import java.util.List;
import java.util.Map;

import com.us.example.bean.User;

public interface UserDao {
     List<User> getList(Map<String,Object> map);
}
