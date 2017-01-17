package com.us.example.serviceImpl;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.us.example.dao.UserDao;

import com.us.example.service.UserService;

/**
 *
 * @ClassName UserServiceImpl
 * @author abel
 * @date 2016年11月10日
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    /**
     *
     * @param map
     * @return
     */
    public Object getList(Map<String, Object> map) {
        return userDao.getList(map);
    }

}