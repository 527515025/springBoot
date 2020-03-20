package cn.abel.user.dao;



import cn.abel.user.models.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
@Mapper
public interface RoleDao {

	List<Role> getByMap(Map<String, Object> map);
	Role getById(Integer id);
	Integer create(Role role);
	int update(Role role);
	int delete(Integer id);
}