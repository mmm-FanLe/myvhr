package com.mayao.mapper;

import com.mayao.entity.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2021-11-25
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

    List<Employee> findAllEmp();

    List<Employee> getEmpByPage(@Param("start") Integer start,@Param("pageSize") Integer pageSize,@Param("keyword") String keyword);

    Integer maxWorkId();

    Integer getEmpAdvancedCount(@Param("emp") Employee employee,@Param("beginDateScope") String[] beginDateScope);

    List<Employee> getEmpByPageAdvanced(@Param("start") Integer start,@Param("pageSize") Integer pageSize,@Param("emp") Employee employee,@Param("beginDateScope") String[] beginDateScope);
}
