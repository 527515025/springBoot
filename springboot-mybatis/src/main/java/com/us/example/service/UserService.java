package com.us.example.service;

import java.util.Map;

import com.us.example.bean.User;

/**
 * The Interface UserService.
 */
public interface UserService {

    /**
     * Gets the list.
     *
     * @param map the map
     * @return the list
     */
    Object getList(Map<String, Object> map);

    void printName();

}