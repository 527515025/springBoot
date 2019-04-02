package cn.abel.service;

import cn.abel.BaseTest;
import cn.abel.bean.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author yyb
 * @time 2019/3/27
 */
public class ServiceTest extends BaseTest {


    @Autowired
    private UserService userService;

    @Test
    public void dynamicDataSourceTest() throws Exception {
        List<User> userList = userService.getByMap(null);
        System.out.println("--userList---" + userList.size());

    }
}
