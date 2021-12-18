package com.mayao.controller;

import com.mayao.entity.RespBean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by mayao on 2021/11/10 10:24 上午
 */
@RestController
@CrossOrigin
public class LoginController {

    @GetMapping("/login")
    public RespBean login(){
        return RespBean.error("尚未登录，请先登录");
    }
}
