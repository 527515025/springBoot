package com.us.example.dao;

import com.us.example.domain.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by yangyibo on 17/1/18.
 */
public interface SysUserRepository extends JpaRepository<SysUser,Long> {

    SysUser findByUsername(String username);

}
