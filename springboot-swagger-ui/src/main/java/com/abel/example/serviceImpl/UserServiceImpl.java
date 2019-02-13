package com.abel.example.serviceImpl;

import com.abel.example.bean.User;
import com.abel.example.dao.UserJpaDao;
import com.abel.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author abel
 * @ClassName UserServiceImpl
 * @date 2016年11月10日
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserJpaDao userJpaDao;

    /**
     * @param username
     * @return
     */
    @Override
    public User getUserByUserName(String username) {
        return userJpaDao.getByUsernameIs(username);
    }

    @Override
    public List<User> getByUsernameContaining(String username) {
        return userJpaDao.findByUsernameContaining(username);
    }

    @Override
    public User saveUser(User user) {
        return userJpaDao.save(user);
    }

    @Override
    public int removeUser(Long id) {
       try {
           userJpaDao.delete(id);
       }catch (Exception e) {
           return 0;
       }
        return 1;
    }

    @Override
    public User updateUser(User user) {
        return userJpaDao.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return  userJpaDao.findOne(id);
    }

    @Override
    public List<User> listUsers() {
        return userJpaDao.findAll();
    }
}
