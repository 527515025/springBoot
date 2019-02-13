package com.yy.example.dao;


import com.yy.example.bean.Permission;

import java.util.List;
import java.util.Map;

public interface PermissionDao {

    List<Permission> getByMap(Map<String, Object> map);

    Permission getById(Integer id);

    Integer create(Permission permission);

    int update(Permission permission);

    List<Permission> getByUserId(Integer userId);

}