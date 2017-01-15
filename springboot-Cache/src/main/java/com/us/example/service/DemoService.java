package com.us.example.service;

import com.us.example.bean.Person;

/**
 * Created by yangyibo on 17/1/13.
 */
public  interface DemoService {
    public Person save(Person person);

    public void remove(Long id);

    public Person findOne(Person person);

}
