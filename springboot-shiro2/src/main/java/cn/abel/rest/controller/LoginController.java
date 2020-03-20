package cn.abel.rest.controller;
import cn.abel.response.ResponseEntity;
import cn.abel.rest.shiro.ShiroUser;
import cn.abel.user.models.User;
import cn.abel.user.service.PermissionService;
import cn.abel.user.service.RoleService;
import cn.abel.user.service.UserService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Sets;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author yyb
 * @time 2020/3/6
 */
@RestController
@CrossOrigin
@RequestMapping("/")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Reference
    private UserService userService;


    /**
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity login() {
        //TODO:登录日志。
        ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        user.setToken(SecurityUtils.getSubject().getSession().getId().toString());
        return ResponseEntity.ok(user);
    }

    /**
     * 获取当前登录人的登录信息，包括拥有的角色、权限等。
     *
     * @return
     */
    @GetMapping("/logininfo")
    public ResponseEntity loginInfo() {
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();

        Map<String, Object> map = new HashMap<>();
        Set<String> permissions = Sets.newHashSet();
        //TODO 获取当前用户所有的权限和角色
        User user = userService.getById(shiroUser.getUserId().intValue());
        map.put("roleList", user.getRoles());
        map.put("permissionList", permissions);
        map.put("userId", shiroUser.getUserId());
        map.put("username", shiroUser.getLoginName());

        return ResponseEntity.ok(map);
    }


    /**
     * 注销
     * @return
     */
    @PostMapping("/logout")
    public ResponseEntity logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return ResponseEntity.ok();
    }


}


