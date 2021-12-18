package com.mayao.controller.config;

import com.mayao.entity.Menu;
import com.mayao.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mayao on 2021/11/12 2:28 下午
 */
@RestController
@CrossOrigin
@RequestMapping("/system/config")
public class SystemConfigController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/menu")
    public List<Menu> getMenusByHrId(){
        /*
            这里之所以不传入id是因为前端的数据不可信，如果用户1在地址栏请求别的用户的id
            也可以得到相应的菜单，这是不安全的。
            这时，我们可以采取从SpringSecurity中的SecurityContextHolder中获取用户的id
         */
        return menuService.getMenusByHrId();
    }
}
