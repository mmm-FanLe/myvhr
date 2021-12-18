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
 * Created by mayao on 2021/11/21 2:49 下午
 */
@RestController
@RequestMapping("/system/hr")
public class HrController {

    @Autowired
    private HrService hrService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/")
    public List<HrVO> getAllHr(){
        return hrService.getAllHr();
    }

    @GetMapping("/{keyword}")
    public List<HrVO> getAllHrByKeyword(@PathVariable("keyword") String keyword){
        return hrService.getAllHrByKeyword(keyword);
    }

    @PutMapping("/")
    public RespBean updateHr(@RequestBody Hr hr){
        if (hrService.updateHr(hr) == 1){
            return RespBean.ok("更新成功");
        }
        return RespBean.error("更新失败");
    }

    @GetMapping("/roles")
    public List<Role> findAllRoles(){
        return roleService.findAllRole();
    }

    @PutMapping("/role")
    public RespBean updateHrRole(Integer hrId,Integer[] rids){
        if (hrService.updateHrRole(hrId,rids)){
            return RespBean.ok("更新成功");
        }
        return RespBean.error("更新失败");
    }

    @DeleteMapping("/{id}")
    public RespBean deleteHrById(@PathVariable("id") Integer id){
        if (hrService.deleteHrById(id) == 1){
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }

}
