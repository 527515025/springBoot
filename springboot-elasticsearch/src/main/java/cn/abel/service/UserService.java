package cn.abel.service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import cn.abel.config.ESRestClient2Config;
import cn.abel.config.ESRestClientConfig;
import cn.abel.constants.Constants;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.abel.dao.UserDao;
import cn.abel.bean.User;

/**
 * @author yyb
 */
@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    protected static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private UserDao userDao;

    /**
     * 数据源1
     */
    @Autowired
    @Qualifier(ESRestClientConfig.NAME)
    private RestClient restClient;

    /**
     * 数据源2
     */
    @Autowired
    @Qualifier(ESRestClient2Config.NAME)
    private RestClient restClient2;


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


    /**
     * 查询用户列表
     *
     * @param keyword   关键字用户名字/用户手机号/用户身份证号
     * @param type      用户类型/1：注册用户/2：转化用户
     * @param startTime 时间范围 起始时间
     * @param endTime   时间范围 结束时间
     * @param pageSize
     * @param pageIndex
     * @return
     * @throws Exception
     */
    public JSONObject getList(String keyword, Integer type, String startTime, String endTime, int pageSize, int pageIndex) throws Exception {
        JsonNode searchResponse;
        JSONObject response = new JSONObject();
        JSONObject search = searchListQuery(keyword, type, startTime, endTime, pageSize, pageIndex);
        //根据档案英文名切换不同的索引
        String endpoint = "/user_Info_index/user/_search";

        searchResponse = esSearch(search, endpoint, restClient);
        if (null == searchResponse) {
            return response;
        }
        JsonNode hitsNode = searchResponse.get("hits");
        ArrayNode hitsArray = (ArrayNode) hitsNode.get("hits");
        response.put("total", hitsNode.get("total").intValue());
        JSONArray list = new JSONArray();
        for (JsonNode hit : hitsArray) {
            JSONObject result = (JSONObject) JSONObject.parse(hit.get("_source").toString());
            list.add(result);
        }
        response.put("list", list);
        return response;
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


    /**
     * 拼装列表查询query
     *
     * @param keyword
     * @return
     */
    private JSONObject searchListQuery(String keyword, Integer type, String startTime, String endTime, int pageSize, int pageIndex) {
        //只返回列表所需数据
        String[] source = new String[]{"id", "user_name", "id_card", "user_name", "mobile", "type", "create_time"};
        JSONObject search = new JSONObject();
        search.put("_source", source);
        if (pageIndex <= 0) {
            pageIndex = 1;
        }
        JSONObject bool = new JSONObject();
        JSONArray sort = new JSONArray();

        //查询类型
        JSONArray mustArray = new JSONArray();
        if (null != type) {
            mustArray.add(getJSONObject("term", getJSONObject("type", type)));
        }
        bool.put("must", mustArray);

        //关键字查询
        if (StringUtils.isNotEmpty(keyword)) {
            JSONObject multiMatch = new JSONObject();
            multiMatch.put("query", keyword);
            multiMatch.put("fields", new String[]{"user_name.keyword", "user_name"});
            //查询条件
            JSONArray shouldArray = new JSONArray();
            //虚化查询
            shouldArray.add(getJSONObject("term", getJSONObject("id_card", keyword)));
            shouldArray.add(getJSONObject("term", getJSONObject("mobile", keyword)));
            shouldArray.add(getJSONObject("multi_match", multiMatch));
            //关键字评分排序
            sort.add(getJSONObject("_score", getJSONObject("order", "desc")));
            //should 必须命中一个
            bool.put("minimum_should_match", 1);
            bool.put("should", shouldArray);
        }

        //排序和时间过滤
        JSONObject rangeField = new JSONObject();
        //筛选创建时间大于 startTime 小于  endTime 的数据
        rangeField.put("gte", startTime);
        rangeField.put("lte", endTime);
        JSONObject range = new JSONObject();
        range.put("range", getJSONObject("create_time", rangeField));
        //按照创建时间规则倒叙排序
        sort.add(getJSONObject("create_time", getJSONObject("order", "desc")));
        bool.put("filter", range);

        search.put("sort", sort);
        search.put("query", getJSONObject("bool", bool));
        return setPage(pageIndex, pageSize, search);
    }


    /**
     * ES查询
     *
     * @param search
     * @return
     */
    public JsonNode esSearch(JSONObject search, String endpoint, RestClient restClient) {
        JsonNode responseNode = null;
        try {
            logger.info("查询es语句为 {}, endpoint 为：{}", search, endpoint.toString());

            Request request = new Request(HttpGet.METHOD_NAME, endpoint);
            if (search != null) {
                String data = search.toString();
                request.setJsonEntity(data);
            }
            try {
                restClient.performRequest(request);
            } catch (IOException e) {
                logger.error("查询es语句报错为 {}", e.getMessage());
            }
            Response response = restClient.performRequest(request);
            String responseStr = EntityUtils.toString(response.getEntity());
            logger.info("查询结果为", responseStr);
            responseNode = mapper.readTree(responseStr);
        } catch (IOException e) {
            logger.error("查询失败", e);
        }
        return responseNode;
    }


    /**
     * 设置页面
     *
     * @param pageIndex
     * @param pageSize
     * @param search
     * @return
     */
    public static JSONObject setPage(Integer pageIndex, Integer pageSize, JSONObject search) {
        search.put("from", (pageIndex - 1) * pageSize);
        search.put("size", pageSize);
        return search;
    }


    /**
     * 获取json 对象
     *
     * @param key
     * @param value
     * @return
     */
    public static JSONObject getJSONObject(String key, Object value) {
        JSONObject term = new JSONObject();
        term.put(key, value);
        return term;
    }
}