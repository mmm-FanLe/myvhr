package com.mayao.mapper;

import com.mayao.entity.MenuRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2021-11-10
 */
@Mapper
public interface MenuRoleMapper extends BaseMapper<MenuRole> {

    Integer insertMidsAndRid(@Param("mids") Integer[] mids, @Param("rid") Integer rid);
}
