package cn.abel.service;

import java.util.List;
import java.util.Map;

import cn.abel.config.DynamicDataSourceContextHolder;
import cn.abel.enums.DatabaseTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.abel.dao.UserDao;
import cn.abel.bean.User;

@Service
public class UserService {
    @Autowired
	private UserDao userDao;
	
	public List<User> getByMap(Map<String,Object> map){
		DynamicDataSourceContextHolder.setDatabaseType(DatabaseTypeEnum.USER);
		return userDao.getByMap(map);
	}
	
	public User getById(Integer id){
		DynamicDataSourceContextHolder.setDatabaseType(DatabaseTypeEnum.USER);
		return userDao.getById(id);
	}
	
	public User create(User user){
		DynamicDataSourceContextHolder.setDatabaseType(DatabaseTypeEnum.USER);
		userDao.create(user);
		return user;
	}
	
	public User update(User user){
		DynamicDataSourceContextHolder.setDatabaseType(DatabaseTypeEnum.USER);
		userDao.update(user);
		return user;
	}
	
	public int delete(Integer id){
		DynamicDataSourceContextHolder.setDatabaseType(DatabaseTypeEnum.USER);
		return userDao.delete(id);
	}
    
}