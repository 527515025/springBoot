package com.abel.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abel.example.bean.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * The Interface UserJpaDao.
 *
 * @author abel
 */
public interface UserJpaDao extends JpaRepository<User, Long> {

    /**
     * Find by name.
     *
     * @param name the name
     * @return the user
     */
    User findByName(String name);

    User getOne(Long id);

    List<User> findByUsernameContaining(String username);

    User getByUsernameIs(String username);

    @Query("select s from User s where name like CONCAT('%',:name,'%')")
    List<User> findByNameLike(@Param("name") String name);
}