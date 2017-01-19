package com.us.example.security;

import com.us.example.dao.SysRoleRepositiory;
import com.us.example.dao.SysUserRepository;
import com.us.example.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        if(user == null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        //用于添加用户的权限。只要把用户权限添加到authorities 就万事大吉。
        authorities.add(new SimpleGrantedAuthority(sysRoleRepositiory.findById(user.getRole_id()).getName()));

        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), authorities);

    }

}
