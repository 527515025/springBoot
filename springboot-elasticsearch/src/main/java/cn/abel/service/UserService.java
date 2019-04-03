package cn.abel.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import cn.abel.constants.Constants;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.abel.dao.UserDao;
import cn.abel.bean.User;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RestClient restClient;

    public List<User> getByMap(Map<String, Object> map) {
        return userDao.getByMap(map);
    }

    public User getById(Integer id) throws Exception {
        //拼接es 的查询query
        String json = Constants.ES_QUERY_JSON_PREFIX + id + Constants.ES_QUERY_JSON_SUFFIX;
        HttpEntity entry = new NStringEntity(json, ContentType.APPLICATION_JSON);
        Response response = restClient.performRequest(HttpGet.METHOD_NAME, Constants.ES_INDEX_PATH, Collections.emptyMap(), entry);
        JSONObject responseJson = JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
        //es 中查询的结果
        System.out.println(responseJson);
        return userDao.getById(id);
    }

    public User create(User user) {
        userDao.create(user);
        return user;
    }

    public User update(User user) {
        userDao.update(user);
        return user;
    }

    public int delete(Integer id) {
        return userDao.delete(id);
    }

}