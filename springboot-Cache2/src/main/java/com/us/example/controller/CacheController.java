package com.us.example.controller;
import com.us.example.bean.Person;
import com.us.example.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by yangyibo on 17/1/13.
 */
@RestController
@RequestMapping(value = "/person")
public class CacheController {

    @Autowired
   private PersonService personService;


    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Person put(Person person){
        return personService.save(person);

    }

    //http://localhost:8080/person/1
    @RequestMapping(value ="/{id}" ,method = RequestMethod.GET)
    @ResponseBody
    public Person cacheable( @PathVariable Long id){
        return personService.findOne(id);

    }

}