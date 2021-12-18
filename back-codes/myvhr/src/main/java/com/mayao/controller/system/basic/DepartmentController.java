package com.mayao.controller.system.basic;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mayao.entity.Department;
import com.mayao.entity.RespBean;
import com.mayao.entity.vo.DepartmentVO;
import com.mayao.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by mayao on 2021/11/21 9:25 上午
 */
@RestController
@RequestMapping("/system/basic/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/")
    public List<DepartmentVO> getAllDepartments(){
        return departmentService.getAllDepartments();
    }


    @PostMapping("/{pid}")
    public RespBean addDepartment(@RequestBody Department department,@PathVariable("pid") Integer pid){
        if (departmentService.addDepartment(department,pid)==1){
            //将添加的数据返回出去
            QueryWrapper<Department> qw = new QueryWrapper<>();
            qw.eq("name",department.getName());
            Department department1 = departmentService.getOne(qw);

            return RespBean.ok("添加成功！",department1);
        }
        return RespBean.error("添加失败！");
    }

    @DeleteMapping("/{pid}")
    public RespBean delDepartment(@RequestBody Department department,@PathVariable("pid") Integer pid){
        Integer del = departmentService.delDepartment(department, pid);
        switch (del){
            case -2:
                return RespBean.error("当前部门下还有子部门,无法删除");
            case -1:
                return RespBean.error("当前部门下还有员工,无法删除");
            case 1:
                return RespBean.ok("删除成功");
            case -3:
                return RespBean.error("父部门修改信息失败");
            default:
                return RespBean.error("删除失败");
        }
    }
}
