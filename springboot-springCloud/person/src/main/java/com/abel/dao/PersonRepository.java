package com.abel.dao;
import com.abel.bean.Person;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by yangyibo on 2018/6/28.
 */
public interface PersonRepository extends JpaRepository<Person, Long>{

}
