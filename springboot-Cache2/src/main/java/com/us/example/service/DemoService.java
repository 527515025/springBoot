package com.us.example.service;

import com.us.example.bean.Person;
import com.us.example.dao.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yangyibo on 17/1/13.
 */
@Service
public class DemoService {

    @Autowired
    private PersonRepository personRepository;


    public List<Person> findAll() {
        return personRepository.findAll();
    }

}