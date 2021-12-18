package com.mayao.service;

import com.mayao.entity.Employee;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mayao.entity.RespPageBean;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-25
 */
public interface EmployeeService extends IService<Employee> {

    List<Employee> findAllEmp();

    RespPageBean getEmpByPage(Integer pageNum, Integer pageSize, String keyword);

    Integer addEmp(Employee employee);

    Integer maxWorkId();

    Integer deleteEmpById(Integer id);

    Integer updateEmp(Employee employee);

    Integer addEmps(List<Employee> list);

    RespPageBean getEmpByPageAdvanced(Integer pageNum, Integer pageSize, Employee employee, String[] beginDateScope);
}
