package com.mayao.mapper;

import com.mayao.entity.Department;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

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
public interface DepartmentMapper extends BaseMapper<Department> {

    List<Department> getTreeDepartment(Integer parentId);
}
