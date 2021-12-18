package com.mayao.mapper;

import com.mayao.entity.Department;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mayao.entity.vo.DepartmentVO;
import org.apache.ibatis.annotations.Mapper;

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
public interface DepartmentMapper extends BaseMapper<Department> {

    List<DepartmentVO> getAllDepartmentsByParentId(Integer pid);
}
