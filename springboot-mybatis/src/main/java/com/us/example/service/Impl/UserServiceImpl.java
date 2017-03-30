package com.us.example.service.Impl;


import java.util.Date;
import java.util.Map;

import com.jf.page.annotation.Page;
import javafx.scene.input.DataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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
    @Page
    @Override
    public Object getList(Map<String, Object> map) {
        return userDao.getList(map);
    }

    //每一秒调用一次  -- 用于测试
    @Scheduled(cron = "0/30 * * * * ?")
    @Override
    public void printName() {
        System.out.println(new Date());
        System.out.println("my name is yang ");
    }

}