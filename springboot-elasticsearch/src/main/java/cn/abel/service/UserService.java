package cn.abel.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.abel.dao.UserDao;
import cn.abel.bean.User;

@Service
public class UserService {
    @Autowired
	private UserDao userDao;
	
	public List<User> getByMap(Map<String,Object> map){
		return userDao.getByMap(map);
	}
	
	public User getById(Integer id){
		return userDao.getById(id);
	}
	
	public User create(User user){
		userDao.create(user);
		return user;
	}
	
	public User update(User user){
		userDao.update(user);
		return user;
	}
	
	public int delete(Integer id){
		return userDao.delete(id);
	}
    
}