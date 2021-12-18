package com.mayao.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mayao.entity.RespBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by mayao on 2021/12/1 2:20 下午
 */
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse resp, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        resp.setCharacterEncoding("utf-8");
        PrintWriter pw = resp.getWriter();
        RespBean error = new RespBean().setMsg("权限不足，请联系管理员").setStatus(403);
        pw.write(new ObjectMapper().writeValueAsString(error));
        pw.flush();
        pw.close();
    }
}
