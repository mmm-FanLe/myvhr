package com.mayao.service;

import com.mayao.entity.Employee;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mayao.entity.vo.EmployeeVO;
import com.mayao.entity.vo.RespPageBean;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-10
 */
public interface EmployeeService extends IService<Employee> {

    RespPageBean getEmployeesByPage(Integer pageNum, Integer pageSize, String keyword);

    Integer addEmp(Employee employeeVO);

    Integer maxWorkID();

    Integer deleteEmployeeById(Integer id);

    Integer updateEmp(Employee employee);

    List<EmployeeVO> findAllEmployeeVO();

    List<Employee> advance(Employee employee);
}
