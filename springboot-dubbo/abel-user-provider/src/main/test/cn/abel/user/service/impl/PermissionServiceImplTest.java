package cn.abel.user.service.impl;

import cn.abel.user.BaseTest;
import cn.abel.user.models.Permission;
import cn.abel.user.service.PermissionService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * @author yyb
 * @time 2020/3/20
 */
public class PermissionServiceImplTest extends BaseTest {
    @Autowired
    private PermissionService permissionService;
    @Test
    public void getByMap() throws Exception {
        permissionService.getByMap(null);
    }

    @Test
    public void getById() throws Exception {
        Permission permission = permissionService.getById(1);
        System.out.println();
    }

    @Test
    public void create() throws Exception {
        Permission permission = new Permission();
        permission.setDescription("234234");
        permission.setName("新增");
        permission.setMethod("nicai");
        permission.setPermissionUrl("zsdfsf_sf");
        permissionService.create(permission);
    }

    @Test
    public void update() throws Exception {
        Permission permission = new Permission();
        permission.setId(1);
        permission.setDescription("234234");
        permission.setName("新增");
        permission.setMethod("nicai");
        permission.setPermissionUrl("zsdfsf_sf");
        permissionService.update(permission);
    }

    @Test
    public void delete() throws Exception {
        permissionService.delete(1);
    }

}