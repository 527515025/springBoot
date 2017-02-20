package com.us.dao;

import com.us.bean.Permission;

import java.util.List;
import java.util.Map;

public interface PermissionDao {

    List<Permission> getByMap(Map<String, Object> map);

    Permission getById(Integer id);

    Integer create(Permission permission);

    int update(Permission permission);

    int delete(Integer id);

    List<Permission> getList();

    List<Permission> getByUserId(Integer userId);

}