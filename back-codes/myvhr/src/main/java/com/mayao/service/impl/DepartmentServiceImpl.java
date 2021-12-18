package com.mayao.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mayao.entity.Department;
import com.mayao.entity.Employee;
import com.mayao.entity.vo.DepartmentVO;
import com.mayao.mapper.DepartmentMapper;
import com.mayao.mapper.EmployeeMapper;
import com.mayao.service.DepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-10
 */
@Service
@Transactional
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public List<DepartmentVO> getAllDepartments() {
        return departmentMapper.getAllDepartmentsByParentId(-1);
    }

    @Override
    public Integer addDepartment(Department department, Integer pid) {

        //1.根据pid查询它的父部门
        QueryWrapper<Department> qw = new QueryWrapper<>();
        qw.eq("id", pid);
        Department parentDep = departmentMapper.selectOne(qw);
        //2.得到父部门的depPath，并将父部门的isParent改为true
        String parentDepPath = parentDep.getDepPath();
        // 如果父部门之前并没有子部门则将他的isParent属性设为true,并修改数据库
        if (!parentDep.getIsParent()) {
            parentDep.setIsParent(true);
            //修改父部门
            int update = departmentMapper.update(parentDep, qw);
            if (update != 1) {
                return -1;
            }
        }
        //3.添加子部门
        department.setEnabled(true);
        department.setParentId(pid);
        int insert = departmentMapper.insert(department);
        if (insert != 1) {
            return -1;
        }
        //4.根据名字查询子部门的id跟父部门的depPath拼接后修改
        qw.clear();
        qw.eq("name", department.getName());
        Department sonDepartment = departmentMapper.selectOne(qw);
        sonDepartment.setDepPath(parentDepPath + "." + sonDepartment.getId());
        int i = departmentMapper.updateById(sonDepartment);
        return i;
    }

    @Override
    public Integer delDepartment(Department department, Integer pid) {
        //1.查询当前部门有多少个子部门
        List<DepartmentVO> children = departmentMapper.getAllDepartmentsByParentId(department.getId());
        //如果有子部门，则不能执行删除
        if (children.size()!=0){
            return -2;
        }
        //如果当前部门有员工，则不能删除
        QueryWrapper<Employee> qw = new QueryWrapper<>();
        qw.eq("departmentId",department.getId());
        List<Employee> employees = employeeMapper.selectList(qw);
        if (employees.size()!=0){
            return -1;
        }
        //2.能执行删除子部门
        //判断父部门有几个子部门，若只有一个则需要修改它的isParent为false
        List<DepartmentVO> children2 = departmentMapper.getAllDepartmentsByParentId(pid);
        QueryWrapper<Department> qw2 = new QueryWrapper<>();
        if (children2.size()==1){
            qw2.eq("id",pid);
            Department depParent = departmentMapper.selectOne(qw2);
            depParent.setIsParent(false);
            int i = departmentMapper.updateById(depParent);
            if (i != 1){
                return -3;
            }
        }
        //3.删除子部门
        return departmentMapper.deleteById(department);
    }


}
