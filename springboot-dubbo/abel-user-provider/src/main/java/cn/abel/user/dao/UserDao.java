package cn.abel.user.dao;


import cn.abel.user.models.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
@Mapper
public interface UserDao {

	List<User> getByMap(Map<String, Object> map);
	User getById(Integer id);
	Integer create(User user);
	int update(User user);
	int delete(Integer id);
	User getByUserName(String userName);
}