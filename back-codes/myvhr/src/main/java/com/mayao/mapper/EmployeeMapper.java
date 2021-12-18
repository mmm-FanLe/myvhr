package com.mayao.mapper;

import com.mayao.entity.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mayao.entity.vo.EmployeeVO;
import com.mayao.entity.vo.RespPageBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2021-11-10
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

    List<EmployeeVO> getEmployeesByPage(@Param("start") Integer start, @Param("pageSize") Integer pageSize,@Param("keyword") String keyword);

    Integer maxWorkID();

    List<Employee> advance(Employee employee);
}
