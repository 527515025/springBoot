package cn.abel.service;

import cn.abel.BaseTest;
import cn.abel.bean.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author yyb
 * @time 2019/3/27
 */
public class ServiceTest extends BaseTest {


    @Autowired
    private UserService userService;

    @Test
    public void dynamicDataSourceTest() throws Exception {
        User user = userService.getById(1);
        System.out.println("--userList---" + user.toString());

    }
}
