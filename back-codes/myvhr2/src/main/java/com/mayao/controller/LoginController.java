package com.mayao.controller;

import com.mayao.entity.RespBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by mayao on 2021/11/25 7:32 下午
 */
@RestController
public class LoginController {

    @GetMapping("/login_page")
    public String loginPage(){
        return "请先登录";
    }
}
