package com.mayao.controller.system.basic;

import com.mayao.entity.Menu;
import com.mayao.entity.RespBean;
import com.mayao.entity.Role;
import com.mayao.entity.vo.MenuVO;
import com.mayao.service.MenuService;
import com.mayao.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by mayao on 2021/11/17 9:50 上午
 */
@RestController
@RequestMapping("/system/basic/permiss")
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
    public List<MenuVO> FindAllMenus(){
        //- 这里用笛卡尔连接处理得到menus的树形结构
        return roleService.FindAllMenus();
    }

    @GetMapping("/mids/{rid}")
    public List<Integer> getMidsByRid(@PathVariable("rid") Integer rid){
        return menuService.getMidsByRid(rid);
    }

    @PutMapping("/")
    public RespBean updateMidsByRid(Integer[] mids,Integer rid){
        if (menuService.updateMidsByRid(mids,rid)){
            return RespBean.ok("修改成功");
        }
        return RespBean.error("修改失败");
    }

    @PostMapping("/role")
    public RespBean addRole(@RequestBody Role role){
        if (roleService.addRole(role) == 1){
            return RespBean.ok("添加成功");
        }
        return RespBean.error("添加失败");
    }

    @DeleteMapping("/role/{rid}")
    public RespBean delRoleById(@PathVariable("rid")Integer rid){
        if (roleService.delRoleById(rid) == 1){
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }

}
