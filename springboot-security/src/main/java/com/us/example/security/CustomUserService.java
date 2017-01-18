package com.us.example.security;

import com.us.example.dao.SysRoleRepositiory;
import com.us.example.dao.SysUserRepository;
import com.us.example.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by yangyibo on 17/1/18.
 */
@Service
public class CustomUserService implements UserDetailsService { //1
    @Autowired
    SysUserRepository userRepository;
    @Autowired
    SysRoleRepositiory sysRoleRepositiory;

    @Override
    public UserDetails loadUserByUsername(String username) { //2

        SysUser user = userRepository.findByUsername(username);
        user.setAuthority(sysRoleRepositiory.findById(user.getRole_id()).getName());
        if(user == null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        return user; //3
    }

}
