package cn.abel.user.service.impl;

import cn.abel.user.dao.UserDao;
import cn.abel.user.models.User;
import cn.abel.user.service.UserService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service(protocol = {"dubbo"}, validation = "false")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public List<User> getByMap(Map<String, Object> map) {
        return userDao.getByMap(map);
    }

    @Override
    public User getById(Integer id) {
        return userDao.getById(id);
    }

    @Override
    public User create(User user) {
        userDao.create(user);
        return user;
    }

    @Override
    public User update(User user) {
        userDao.update(user);
        return user;
    }

    @Override
    public int delete(Integer id) {
        return userDao.delete(id);
    }

    @Override
    public User getByUserName(String userName) {
        return userDao.getByUserName(userName);
    }

}