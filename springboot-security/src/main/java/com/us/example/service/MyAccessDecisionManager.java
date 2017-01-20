package com.us.example.service;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by yangyibo on 17/1/19.
 */
@Service
public class MyAccessDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {

        if(null== configAttributes || configAttributes.size() <=0) {
            return;
        }
        ConfigAttribute c;
        String needRole;
        for(Iterator<ConfigAttribute> iter = configAttributes.iterator(); iter.hasNext(); ) {
            c = iter.next();
            needRole = c.getAttribute();
            for(GrantedAuthority ga : authentication.getAuthorities()) {
                if(needRole.trim().equals(ga.getAuthority())) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("no right");
    }



    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
