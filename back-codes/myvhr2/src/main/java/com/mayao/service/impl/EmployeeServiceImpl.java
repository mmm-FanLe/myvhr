package com.mayao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mayao.entity.Employee;
import com.mayao.entity.RespPageBean;
import com.mayao.mapper.EmployeeMapper;
import com.mayao.service.EmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-25
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    DecimalFormat decimalFormat = new DecimalFormat("##.00");

    @Override
    public List<Employee> findAllEmp() {
        return employeeMapper.findAllEmp();
    }

    @Override
    public RespPageBean getEmpByPage(Integer pageNum, Integer pageSize,String keyword) {
        Integer start = null;
        if (pageNum != null && pageSize != null) {
            start = (pageNum - 1) * pageSize;
        }
        //- 分页查询员工
        List<Employee> emps = employeeMapper.getEmpByPage(start, pageSize, keyword);
        //- 查询员工总数
        QueryWrapper<Employee> qw = new QueryWrapper<>();
        qw.like("name", keyword);
        Integer total = employeeMapper.selectCount(qw);


        return new RespPageBean(total.longValue(), emps);
    }

    @Override
    public Integer addEmp(Employee employee) {

        //- 计算合同期限
        int year = employee.getEndContract().getYear() - employee.getBeginContract().getYear();
        int month = employee.getEndContract().getMonthValue() - employee.getBeginContract().getMonthValue();
        Double totalMonth = Double.valueOf(year * 12 + month);

        double v = Double.parseDouble(decimalFormat.format(totalMonth / 12));
        employee.setContractTerm(v);
        return employeeMapper.insert(employee);
    }

    @Override
    public Integer maxWorkId() {
        return employeeMapper.maxWorkId();
    }

    @Override
    public Integer deleteEmpById(Integer id) {
        return employeeMapper.deleteById(id);
    }

    @Override
    public Integer updateEmp(Employee employee) {
        return employeeMapper.updateById(employee);
    }

    @Override
    public Integer addEmps(List<Employee> list) {
        Integer i = 0;
        for (Employee emp : list) {
            employeeMapper.insert(emp);
            i++;
        }
        return i;
    }

    @Override
    public RespPageBean getEmpByPageAdvanced(Integer pageNum, Integer pageSize, Employee employee, String[] beginDateScope) {
        Integer start = null;
        if (pageNum != null && pageSize != null) {
            start = (pageNum - 1) * pageSize;
        }
        //- 符合查询条件的员工总数
        Integer count = employeeMapper.getEmpAdvancedCount(employee, beginDateScope);
        //- 分页查询员工
        List<Employee> emps = employeeMapper.getEmpByPageAdvanced(start, pageSize, employee,beginDateScope);
        return new RespPageBean(count.longValue(),emps);
    }

}
