package com.mayao.controller.system.basic;

import com.mayao.entity.Menu;
import com.mayao.entity.RespBean;
import com.mayao.entity.Role;
import com.mayao.service.MenuService;
import com.mayao.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by mayao on 2021/12/4 9:13 上午
 */
@RestController
@RequestMapping("/system/basic/permission")
public class PermissionController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @GetMapping("/")
    public List<Role> findAllRole(){
        return roleService.findAllRole();
    }

    @GetMapping("/menus")
    public List<Menu> findAllMenus(){
        return menuService.findAllMenus();
    }

    @GetMapping("/mid/{rid}")
    public List<Integer> getMidsByRid(@PathVariable("rid") Integer rid){
        return menuService.getMidsByRid(rid);
    }

    @PutMapping("/")
    public RespBean updateMidsByRid(Integer rid,Integer[] mids){
        if (menuService.updateMidsByRid(mids,rid)){
            return RespBean.ok("修改权限成功");
        }
        return RespBean.error("修改权限失败");
    }

    @PostMapping("/")
    public RespBean addRole(@RequestBody Role role){
        if (roleService.addRole(role) == 1){
            return RespBean.ok("添加角色成功");
        }
        return RespBean.error("添加角色失败");
    }

    @DeleteMapping("/role/{rid}")
    public RespBean deleteRoleById(@PathVariable("rid") Integer id){
        if (roleService.deleteRoleById(id) == 1){
            return RespBean.ok("删除角色成功");
        }
        return RespBean.error("删除角色失败");
    }



}
