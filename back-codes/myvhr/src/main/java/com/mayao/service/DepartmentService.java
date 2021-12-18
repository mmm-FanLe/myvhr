package com.mayao.service;

import com.mayao.entity.Department;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mayao.entity.vo.DepartmentVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-10
 */
public interface DepartmentService extends IService<Department> {

    List<DepartmentVO> getAllDepartments();

    Integer addDepartment(Department department,Integer pid);

    Integer delDepartment(Department department, Integer pid);
}
