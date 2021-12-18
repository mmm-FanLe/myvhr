package com.mayao.service;

import com.mayao.entity.Department;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-25
 */
public interface DepartmentService extends IService<Department> {

    List<Department> getTreeDepartment();

    Integer addDepartment(Integer pid, Department department);

    Integer delDepartment(Integer pid, Department department);
}
