package com.us.example.service;

import com.us.example.dao.UserDao;
import com.us.example.domain.SysUser;
import com.us.example.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by yangyibo on 17/3/2.
 */
@Service
public class UserService {
    @Autowired
    UserDao userDao;

    public SysUser create(SysUser sysUser){
        //进行加密
        BCryptPasswordEncoder encoder =new BCryptPasswordEncoder();
        sysUser.setPassword(encoder.encode(MD5Util.encode(sysUser.getRawPassword().trim())));
        userDao.create(sysUser);
    return sysUser;
    }

}
