package cn.abel.user.service.impl;

import cn.abel.user.dao.PermissionDao;
import cn.abel.user.models.Permission;
import cn.abel.user.service.PermissionService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service(protocol = {"dubbo"}, validation = "false")
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionDao permissionDao;

    @Override
    public List<Permission> getByMap(Map<String, Object> map) {
        return permissionDao.getByMap(map);
    }

    @Override
    public Permission getById(Integer id) {
        return permissionDao.getById(id);
    }

    @Override
    public Permission create(Permission permission) {
        permissionDao.create(permission);
        return permission;
    }

    @Override
    public Permission update(Permission permission) {
        permissionDao.update(permission);
        return permission;
    }

    @Override
    public int delete(Integer id) {
        return permissionDao.delete(id);
    }

}