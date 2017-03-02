package com.us.example.config;

import com.us.example.bean.Person;
import com.us.example.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangyibo on 17/3/2.
 */
@Configuration
public class CacheConfig {

    @Autowired
    private DemoService demoService;

    @Bean
    public CacheManager getCacheManager() {
        List<Person> personList = demoService.findAll();
        //所有缓存的名字
        List<String> cacheNames = new ArrayList();
        GuavaCacheManager cacheManager = new GuavaCacheManager();
        //GuavaCacheManager 的数据结构类似  Map<String,Map<Object,Object>>  map =new HashMap<>();

        //将数据放入缓存
        personList.stream().forEach(person -> {
            //用person 的id cacheName
            String cacheName=person.getId().toString();
            if(cacheManager.getCache(cacheName)==null){
                //为每一个person 如果不存在，创建一个新的缓存对象
                cacheNames.add(cacheName);
                cacheManager.setCacheNames(cacheNames);
            }
            Cache cache = cacheManager.getCache(cacheName);
            //缓存对象用person的id当作缓存的key 用person 当作缓存的value
            cache.put(person.getId(),person);
            System.out.println("为 ID 为"+cacheName+ "的person 数据做了缓存");
        });
        return cacheManager;
    }
}
