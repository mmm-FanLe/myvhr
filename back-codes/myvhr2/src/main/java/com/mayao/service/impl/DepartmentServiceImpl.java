package com.mayao.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mayao.entity.Department;
import com.mayao.entity.Employee;
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
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-25
 */
@Service
@Transactional
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public List<Department> getTreeDepartment() {
        return departmentMapper.getTreeDepartment(-1);
    }

    @Override
    public Integer addDepartment(Integer pid, Department department) {
        //1.先根据pid查询父部门得到父部门的depPath
        Department parentDep = departmentMapper.selectById(pid);
        String parentDepPath = parentDep.getDepPath();
        //2.得到父部门的isParent属性，如果父部门是叶子节点则修改isParent属性为true
        Boolean isParent = parentDep.getIsParent();
        if (!isParent){
            parentDep.setIsParent(true);
            //修改数据库
            int update = departmentMapper.updateById(parentDep);
            if (update != 1){
                return -2;
            }
        }
        //3.添加新部门
        //department.setDepPath(parentDepPath+"."+department.getId());//这样写得不到新部门的id,因为没有传id过来，id是数据库自己生成的
        department.setParentId(parentDep.getId());
        department.setEnabled(true);
        department.setIsParent(false);
        //先添加进去再做修改
        departmentMapper.insert(department);
        QueryWrapper<Department> qw = new QueryWrapper<>();
        qw.eq("name",department.getName());
        Department newDep = departmentMapper.selectOne(qw);
        newDep.setDepPath(parentDepPath+"."+newDep.getId());
        return departmentMapper.updateById(newDep);
    }

    @Override
    public Integer delDepartment(Integer pid, Department department) {
        //1.如果当前部门下还有子部门则不能删除
        Department department1 = departmentMapper.selectById(department.getId());
        if (department1.getIsParent()){
            return -2;
        }
        //2.如果当前部门下还有员工则不能删除
        QueryWrapper<Employee> qw = new QueryWrapper<>();
        qw.eq("departmentId",department1.getId());
        Integer count = employeeMapper.selectCount(qw);
        if (count > 0){
            return -3;
        }
        //3.如果父部门只有要被删除部门一个孩子，则需要先修改父部门的isParent属性
        QueryWrapper<Department> qw1 = new QueryWrapper<>();
        qw1.eq("parentId",pid);
        List<Department> children = departmentMapper.selectList(qw1);
        if (children.size() == 1){
            //修改父部门
            qw1.clear();
            qw1.eq("id",pid);
            Department parentDep = departmentMapper.selectOne(qw1);
            parentDep.setIsParent(false);
            int update = departmentMapper.updateById(parentDep);
            if (update != 1){
                return -4;
            }
        }
        //4.删除该部门
        return departmentMapper.deleteById(department);
    }
}
