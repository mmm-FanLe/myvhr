package com.mayao.config;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 判断当前用户是否具备某个角色
 * Created by mayao on 2021/11/13 10:30 上午
 */
@Component
public class CustomUrlDecisionManager implements AccessDecisionManager {

    /**
     * ! 这个方法如果抛出异常则说明请求不合法，没抛异常就是合法
     * ! 该方法最后还是会抛异常，所以如果是合法请求则直接return
     * @param authentication  用户保存的角色
     * @param object
     * @param configAttributes 请求某个路径需要的角色
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        for (ConfigAttribute configAttribute : configAttributes) {
            String needRole = configAttribute.getAttribute();
            //如果传的是默认值则说明该请求路径请求的资源是登陆之后就可以访问的
            if ("ROLE_LOGIN".equals(needRole)){
                //判断该用户是否登录
                if (authentication instanceof AnonymousAuthenticationToken){
                    throw new AccessDeniedException("尚未登录，请先登录!");
                }else{
                    return;
                }
            }
            //获取当前用户登录的角色
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            //将需要的角色与该用户具有的角色进行比较,如果authorities中具有configAttributes中的任意一个则说明有权限
            for (GrantedAuthority authority : authorities) {
                //请求路径合法
                if (authority.getAuthority().equals(needRole)){
                    return;
                }
            }
        }
        //没有匹配的角色
        throw new AccessDeniedException("权限不足，请联系管理员！");
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
