package com.us.example.domain;


import javax.persistence.*;

/**
 * Created by yangyibo on 17/1/17.
 */

@Entity
@Table(name = "Sys_User")
public class SysUser { //用户实体实现UserDetails接口,我门的用户实体即位 security 所使用的用户

    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String password;
    private Integer role_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }

}
