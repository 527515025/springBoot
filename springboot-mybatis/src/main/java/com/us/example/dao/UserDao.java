package com.us.example.dao;

import java.util.List;
import java.util.Map;

import com.us.example.bean.User;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component("userDao")
public interface UserDao {
     List<User> getList(Map<String,Object> map);
}
