package com.mayao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mayao.entity.*;
import com.mayao.entity.vo.EmployeeVO;
import com.mayao.entity.vo.RespPageBean;
import com.mayao.mapper.*;
import com.mayao.service.EmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
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
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private JoblevelMapper joblevelMapper;

    @Autowired
    private PositionMapper positionMapper;

    @Autowired
    private NationMapper nationMapper;

    @Autowired
    private PoliticsstatusMapper politicsstatusMapper;

    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
    DecimalFormat decimalFormat = new DecimalFormat("##.00");

    @Override
    public RespPageBean getEmployeesByPage(Integer pageNum, Integer pageSize, String keyword) {
        Integer start = (pageNum - 1) * pageSize;
        //- 查询员工信息
        List<EmployeeVO> employees = employeeMapper.getEmployeesByPage(start, pageSize, keyword);
        //- 查询员工总数
        QueryWrapper<Employee> qw = new QueryWrapper<>();
        qw.like("name", keyword);
        Integer total = employeeMapper.selectCount(qw);

        return new RespPageBean(total.longValue(), employees);
    }

    @Override
    public Integer addEmp(Employee employee) {
        //- 计算合同期限
        Date beginContract = employee.getBeginContract();
        Date endContract = employee.getEndContract();
        //- 计算出相差的总月份
        Double totalMonth = (Double.parseDouble(yearFormat.format(endContract))-Double.parseDouble(yearFormat.format(beginContract)))*12+(Double.parseDouble(monthFormat.format(endContract))-Double.parseDouble(monthFormat.format(beginContract)));

        employee.setContractTerm(Double.parseDouble(decimalFormat.format(totalMonth/12)));

        return employeeMapper.insert(employee);
    }

    @Override
    public Integer maxWorkID() {
        return employeeMapper.maxWorkID();
    }

    @Override
    public Integer deleteEmployeeById(Integer id) {
        return employeeMapper.deleteById(id);
    }

    @Override
    public Integer updateEmp(Employee employee) {
        return employeeMapper.updateById(employee);
    }

    @Override
    public List<EmployeeVO> findAllEmployeeVO() {
        return employeeMapper.getEmployeesByPage(null,null,null);
    }

    @Override
    public List<Employee> advance(Employee employee) {
        return employeeMapper.advance(employee);
    }


    public static void main(String[] args) {
        LocalDate date3 = LocalDate.of(2021, 1, 1);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String format = date3.format(dateTimeFormatter);
        System.out.println("format = " + format);
    }
}
