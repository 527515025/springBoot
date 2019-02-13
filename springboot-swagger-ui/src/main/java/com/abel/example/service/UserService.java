package com.abel.example.service;

import java.util.List;

import com.abel.example.bean.User;

/**
 * The Interface UserService.
 */
public interface UserService {

	/**
	 * Gets the user by name.
	 *
	 * @param username the user name
	 * @return the user by name
	 */
	public User getUserByUserName(String username);

	public List<User> getByUsernameContaining(String username);

	/**
	 * 增加学生
	 * @param user
	 * @return
	 */
	User saveUser(User user);

	/**
	 * 删除单个学生
	 * @param id
	 * @return
	 */
	int removeUser(Long id);

	/**
	 * 更新学生信息
	 * @param user
	 * @return
	 */
	User updateUser(User user);

	/**
	 * 根据id获取学生信息
	 * @param id
	 * @return
	 */
	User getUserById(Long id);

	/**
	 * 获取学生列表
	 * @return
	 */
	List<User> listUsers();
}
