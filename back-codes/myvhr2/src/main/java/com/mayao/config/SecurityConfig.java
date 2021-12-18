package com.mayao.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mayao.entity.Hr;
import com.mayao.entity.RespBean;
import com.mayao.filter.MyFilter;
import com.mayao.service.HrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.PrintWriter;

/**
 * Created by mayao on 2021/11/26 2:23 下午
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private HrService hrService;

    @Autowired
    private MyFilterInvocation myFilterInvocation;

    @Autowired
    private MyDecisionManager myDecisionManager;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(hrService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //用对象配置antMather
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setSecurityMetadataSource(myFilterInvocation);
                        object.setAccessDecisionManager(myDecisionManager);
                        return object;
                    }
                })
                .and()
                .formLogin()
                .usernameParameter("username").passwordParameter("password")
                .loginProcessingUrl("/doLogin")
                .loginPage("/login_page")
                .successHandler((req, resp, authentication) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter pw = resp.getWriter();
                    Hr hr = (Hr) authentication.getPrincipal();
                    hr.setPassword(null);
                    pw.write(new ObjectMapper().writeValueAsString(RespBean.ok("登陆成功", hr)));
                    pw.flush();
                    pw.close();
                })
                .failureHandler((req, resp, e) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    RespBean respBean = RespBean.error("登录失败");
                    if (e instanceof LockedException) {
                        respBean.setMsg("账户被锁定，请联系管理员");
                    } else if (e instanceof DisabledException) {
                        respBean.setMsg("账户被禁用，请联系管理员");
                    } else if (e instanceof BadCredentialsException) {
                        respBean.setMsg("用户名或密码错误，请重新输入");
                    } else if (e instanceof AccountExpiredException) {
                        respBean.setMsg("账户过期，请联系管理员");
                    }

                    PrintWriter pw = resp.getWriter();
                    pw.write(new ObjectMapper().writeValueAsString(respBean));
                    pw.flush();
                    pw.close();
                })
                .permitAll()
                .and()
                .logout()
                .logoutSuccessHandler((req, resp, authentication) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter pw = resp.getWriter();
                    pw.write(new ObjectMapper().writeValueAsString(RespBean.ok("注销成功")));
                    pw.flush();
                    pw.close();
                })
                .and()
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint((req, resp, e) -> {
                    resp.setContentType("application/json;charset=utf-8");
                    //- 这里给出状态码后，会被axios的response拦截器处理。当后端重启或用户长时间挂机就让他重新登录
                    resp.setStatus(401);
                    PrintWriter pw = resp.getWriter();
                    RespBean error = RespBean.error("访问失败");
                    if (e instanceof InsufficientAuthenticationException) {
                        error.setMsg("111请求失败,请联系管理员!");
                    }
                    pw.write(new ObjectMapper().writeValueAsString(error));
                    pw.flush();
                    pw.close();
                })
                .accessDeniedHandler(new MyAccessDeniedHandler())
        ;

        http.addFilterAt(myFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    MyFilter myFilter() throws Exception {
        MyFilter myFilter = new MyFilter();
        myFilter.setFilterProcessesUrl("/jsonLogin");
        myFilter.setAuthenticationSuccessHandler((req, resp, authentication) -> {
            resp.setContentType("application/json;charset=utf-8");
            PrintWriter pw = resp.getWriter();
            Hr hr = (Hr) authentication.getPrincipal();
            hr.setPassword(null);
            pw.write(new ObjectMapper().writeValueAsString(RespBean.ok("登陆成功", hr)));
            pw.flush();
            pw.close();
        });

        myFilter.setAuthenticationFailureHandler((req, resp, e) -> {
            resp.setContentType("application/json;charset=utf-8");
            RespBean respBean = RespBean.error("登录失败");
            if (e instanceof LockedException) {
                respBean.setMsg("账户被锁定，请联系管理员");
            } else if (e instanceof DisabledException) {
                respBean.setMsg("账户被禁用，请联系管理员");
            } else if (e instanceof BadCredentialsException) {
                respBean.setMsg("用户名或密码错误，请重新输入");
            } else if (e instanceof AccountExpiredException) {
                respBean.setMsg("账户过期，请联系管理员");
            }

            PrintWriter pw = resp.getWriter();
            pw.write(new ObjectMapper().writeValueAsString(respBean));
            pw.flush();
            pw.close();
        });

        myFilter.setAuthenticationManager(super.authenticationManager());
        return myFilter;
    }

    //@Bean
    /*CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedOrigin("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",configuration);
        return source;
    }*/
}
