package com.mayao.controller.system.basic;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mayao.entity.Department;
import com.mayao.entity.RespBean;
import com.mayao.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by mayao on 2021/12/5 9:18 上午
 */
@RestController
@RequestMapping("/system/basic/dep")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/")
    public List<Department> getTreeDepartment(){
        return departmentService.getTreeDepartment();
    }

    @PostMapping("/{pid}")
    public RespBean addDepartment(@PathVariable("pid") Integer pid,@RequestBody Department department){
        if (departmentService.addDepartment(pid,department) == 1){
            QueryWrapper<Department> qw = new QueryWrapper<>();
            qw.eq("name",department.getName());
            Department newDep = departmentService.getOne(qw);
            return RespBean.ok("添加部门成功",newDep);
        }else if (departmentService.addDepartment(pid,department) == -2){
            return RespBean.error("修改父部门失败");
        }
        return RespBean.error("添加部门失败");
    }

    @DeleteMapping("/{pid}")
    public RespBean delDepartment(@PathVariable("pid") Integer pid,@RequestBody Department department){
        Integer res = departmentService.delDepartment(pid,department);
        switch (res){
            case 1:
                return RespBean.ok("删除部门成功");
            case -1:
                return RespBean.error("删除部门失败");
            case -2:
                return RespBean.error("删除失败：当前部门下还有子部门" );
            case -3:
                return RespBean.error("删除失败：当前部门下还有员工");
            default:
                return RespBean.error("删除失败：父部门信息修改失败");
        }
    }


}
