package com.mayao.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mayao.entity.Hr;
import com.mayao.entity.RespBean;
import com.mayao.service.HrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by mayao on 2021/11/10 8:58 上午
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private HrService hrService;

    @Autowired
    private CustomFilterInvocationSecurityMetadataSource customFilterInvocationSecurityMetadataSource;

    @Autowired
    private CustomUrlDecisionManager customDecisionManager;

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(hrService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // super.configure(http);
        http.authorizeRequests()
                //.anyRequest().authenticated()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setAccessDecisionManager(customDecisionManager);
                        object.setSecurityMetadataSource(customFilterInvocationSecurityMetadataSource);
                        return object;
                    }
                })
                .and()
                .formLogin()
                .usernameParameter("username").passwordParameter("password")
                .loginProcessingUrl("/doLogin")
                .loginPage("/login")
                .successHandler((req,resp,authentication)->{
                    resp.setContentType("application/json;charset=utf-8");
                    //resp.setStatus(401);
                    PrintWriter pw = resp.getWriter();
                    Hr hr = (Hr) authentication.getPrincipal();
                    hr.setPassword(null);//别把密码返回给前端
                    RespBean ok = RespBean.ok("登陆成功", hr);
                    String s = new ObjectMapper().writeValueAsString(ok);
                    pw.write(s);
                    pw.flush();
                    pw.close();
                })
                .failureHandler((req,resp,e)->{
                    resp.setContentType("application/json;charset=utf-8");
                    //resp.setStatus(401);
                    RespBean error = RespBean.error("登录失败");
                    PrintWriter pw = resp.getWriter();
                    if (e instanceof LockedException){
                        error.setMsg("账户被锁定，请联系管理员");
                    }else if(e instanceof CredentialsExpiredException){
                        error.setMsg("密码过期，请联系管理员");
                    }else if (e instanceof AccountExpiredException){
                        error.setMsg("账户过期，请联系管理员");
                    }else if (e instanceof DisabledException){
                        error.setMsg("账户被禁用，请联系管理员");
                    }else if (e instanceof BadCredentialsException){
                        error.setMsg("用户名或密码错误，请重新输入");
                    }
                    pw.write(new ObjectMapper().writeValueAsString(error));
                    pw.flush();
                    pw.close();

                })
                .permitAll()
                .and()
                .logout()
                .logoutSuccessHandler((req,resp,authentication)->{
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter pw = resp.getWriter();
                    pw.write(new ObjectMapper().writeValueAsString(RespBean.ok("注销登录成功")));
                    pw.flush();
                    pw.close();
                })
                .permitAll()
                .and()
                .csrf().disable()
                //没有认证就访问其他资源时,在这里处理结果，不要重定向
                .exceptionHandling().authenticationEntryPoint(new AuthenticationEntryPoint() {
                    @Override
                    public void commence(HttpServletRequest req, HttpServletResponse resp, AuthenticationException authException) throws IOException, ServletException {
                        resp.setContentType("application/json;charset=utf-8");
                        //- 后端重新启动时，前端访问资源会出现异常，在这里给个状态码，前端针对这个状态码做处理，直接跳回登录页
                        resp.setStatus(401);
                        RespBean error = RespBean.error("访问失败");
                        PrintWriter pw = resp.getWriter();
                        if (authException instanceof InsufficientAuthenticationException){
                            error.setMsg("请求失败,请联系管理员!");
                        }
                        pw.write(new ObjectMapper().writeValueAsString(error));
                        pw.flush();
                        pw.close();
                    }
                })
        ;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/login");
    }
}
