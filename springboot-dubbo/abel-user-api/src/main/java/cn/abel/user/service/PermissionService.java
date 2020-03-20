package cn.abel.user.service;

import cn.abel.user.models.Permission;

import java.util.List;
import java.util.Map;

public interface PermissionService {

    List<Permission> getByMap(Map<String, Object> map);

    Permission getById(Integer id);

    Permission create(Permission permission);


    Permission update(Permission permission);

    int delete(Integer id);

}