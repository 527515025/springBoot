package com.abel.bean;

/**
 * Created by yangyibo on 2018/6/29.
 */
public class Person {
    private Long id;

    private String name;


    public Person() {
        super();
    }

    public Person(String name) {
        super();
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}