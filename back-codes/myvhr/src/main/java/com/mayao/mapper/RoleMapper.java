package com.mayao.mapper;

import com.mayao.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface RoleMapper extends BaseMapper<Role> {

    //根据id查询角色
    List<Role> selectRolesByHrId(Integer id);

}
