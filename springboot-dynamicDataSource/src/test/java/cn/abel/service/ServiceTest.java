package cn.abel.service;

import cn.abel.BaseTest;
import cn.abel.bean.News;
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
    private NewsService newsService;
    @Autowired
    private UserService userService;

    @Test
    public void dynamicDataSourceTest() throws Exception {
        List<News> newsList = newsService.getByMap(null);
        List<User> userList = userService.getByMap(null);
        System.out.println("newsList----" + newsList.size() + "--userList---" + userList.size());

    }
}
