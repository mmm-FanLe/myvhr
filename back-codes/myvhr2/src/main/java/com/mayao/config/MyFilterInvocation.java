package com.mayao.config;

import com.mayao.entity.Menu;
import com.mayao.entity.Role;
import com.mayao.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * Created by mayao on 2021/11/26 4:49 下午
 */
@Component
public class MyFilterInvocation implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private MenuService menuService;

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    //事先加载所有与数据库匹配的路径所需要的角色
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        List<Menu> menus = menuService.getMenusWithRoles();
        for (Menu menu : menus) {
            //查看该路径是否与数据库中的匹配
            if (antPathMatcher.match(menu.getUrl(),requestUrl)){
                List<Role> roles = menu.getRoles();
                String[] strs=  new String[roles.size()];
                for (int i = 0; i < roles.size(); i++) {
                    strs[i] = roles.get(i).getName();
                }
                return SecurityConfig.createList(strs);
            }
        }
        //该路径表示用户登录后就可以访问
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
