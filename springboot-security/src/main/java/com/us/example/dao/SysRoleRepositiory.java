package com.us.example.dao;

import com.us.example.domain.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by yangyibo on 17/1/18.
 */
public interface SysRoleRepositiory  extends JpaRepository<SysRole,Integer> {

     SysRole  findById(Integer id);
}
