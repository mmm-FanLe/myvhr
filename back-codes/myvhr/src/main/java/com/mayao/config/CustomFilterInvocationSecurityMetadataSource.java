package com.mayao.config;

import com.mayao.entity.Menu;
import com.mayao.entity.Role;
import com.mayao.entity.vo.MenuVO2;
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
 *
 * 这个类的作用，主要是根据用户传来的请求地址，分析出请求需要的角色
 * Created by mayao on 2021/11/13 9:59 上午
 */
@Component
public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private MenuService menuService;

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        //得到请求路径
        String url = ((FilterInvocation) object).getRequestUrl();
        List<MenuVO2> menus = menuService.getAllMenusWithRole();
        for (MenuVO2 menu : menus) {
            //查看路径是否与数据库中的路径匹配
            if (antPathMatcher.match(menu.getUrl(),url)){
                //如果匹配就给这个路径绑定需要的角色信息
                List<Role> roles = menu.getRoles();
                String[] strs = new String[roles.size()];
                for (int i = 0; i < roles.size(); i++) {
                    strs[i] = roles.get(i).getName();
                }
                return SecurityConfig.createList(strs);
            }
        }

        //如果请求路径与数据库中的路径没有匹配上，则表示都是`登录之后`可以访问的，所以给一个默认值
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
