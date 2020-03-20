package cn.abel.user.service;


import cn.abel.user.models.Role;

import java.util.List;
import java.util.Map;

public interface RoleService {


    List<Role> getByMap(Map<String, Object> map);

    Role getById(Integer id);

    Role create(Role role);

    Role update(Role role);

    int delete(Integer id);

}