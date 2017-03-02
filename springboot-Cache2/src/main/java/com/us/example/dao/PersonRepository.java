package com.us.example.dao;

import com.us.example.bean.Person;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by yangyibo on 17/1/13.
 */
public interface PersonRepository extends JpaRepository<Person, Long> {

}
