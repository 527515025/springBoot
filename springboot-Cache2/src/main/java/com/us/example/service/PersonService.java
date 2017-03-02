package com.us.example.service;

import com.us.example.bean.Person;
import com.us.example.dao.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

/**
 * Created by yangyibo on 17/3/2.
 */
@Service
public class PersonService {
    @Autowired
    CacheManager cacheManager;

    @Autowired
    private PersonRepository personRepository;


    public Person findOne(Long id) {
        Person person = getCache(id, cacheManager);
        if (person != null) {
            System.out.println("从缓存中取出:" + person.toString());
        } else {
            person = personRepository.findOne(id);
            System.out.println("从数据库中取出:" + person.toString());

        }
        return person;
    }

    public Person save(Person person) {
        Person p = personRepository.save(person);
        return p;
    }


    public Person getCache(Long id, CacheManager cacheManager) {

//   Person person=(Person) cacheManager.getCache(id.toString()).get(id).get();

        Cache cache = cacheManager.getCache(id.toString());
        Cache.ValueWrapper valueWrapper = cache.get(id);
        Person person = (Person) valueWrapper.get();
        return person;
    }
}
