package com.mayao.controller.base;

import com.mayao.entity.Menu;
import com.mayao.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by mayao on 2021/11/26 4:36 下午
 */
@RestController
@RequestMapping("/system/config")
public class MenusController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/menus")
    public List<Menu> menus(){
        return menuService.getMenusByHrId();
    }

}
