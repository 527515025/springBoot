package cn.abel.user.service.impl;


import cn.abel.user.dao.RoleDao;
import cn.abel.user.models.Role;
import cn.abel.user.service.RoleService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service(protocol = {"dubbo"}, validation = "false")
public class RoleServiceImpl implements RoleService{
    @Autowired
	private RoleDao roleDao;
	@Override
	public List<Role> getByMap(Map<String,Object> map) {
	    return roleDao.getByMap(map);
	}
	@Override
	public Role getById(Integer id) {
		return roleDao.getById(id);
	}
	@Override
	public Role create(Role role) {
		roleDao.create(role);
		return role;
	}
	@Override
	public Role update(Role role) {
		roleDao.update(role);
		return role;
	}
	@Override
	public int delete(Integer id) {
		return roleDao.delete(id);
	}
    
}