package com.mayao.controller.system;

import com.mayao.entity.Hr;
import com.mayao.entity.RespBean;
import com.mayao.entity.Role;
import com.mayao.entity.vo.HrVO;
import com.mayao.service.HrService;
import com.mayao.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by mayao on 2021/12/7 8:54 上午
 */
@RestController
@RequestMapping("/system/hr")
public class HrController {

    @Autowired
    private HrService hrService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/")
    public List<HrVO> findAllHrExceptCurrentUser(){
        return hrService.findAllHrExceptCurrentUser();
    }

    @GetMapping("/{keyword}")
    public List<Hr> findHrByName(@PathVariable("keyword") String keyword){
        return hrService.findHrByName(keyword);
    }

    /**
     * - 必须传enabled字段，这是spring security判断用户是否禁用的标志
     * @param hr
     * @return
     */
    @PutMapping("/")
    public RespBean updateHr(@RequestBody Hr hr){
        if (hrService.updateHr(hr) == 1){
            return RespBean.ok("修改用户成功");
        }
        return RespBean.error("修改用户失败");
    }

    @DeleteMapping("/{id}")
    public RespBean deleteHrById(@PathVariable("id") Integer id){
        if (hrService.deleteHrById(id) == 1){
            return RespBean.ok("删除用户成功");
        }
        return RespBean.error("删除用户失败");
    }

    @GetMapping("/roles")
    public List<Role> findAllRoles(){
        return roleService.findAllRole();
    }

    @PutMapping("/role")
    public RespBean updateRolesOfHrByRids(Integer hrId,Integer[] rids){
        if (hrService.updateRolesOfHrByRids(hrId,rids) == rids.length){
            return RespBean.ok("修改用户的角色成功");
        }
        return RespBean.error("修改用户的角色失败");
    }

}
