package com.mayao.mapper;

import com.mayao.entity.Menu;
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
public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> getMenusByHrId(Integer id);

    List<Menu> getMenusWithRoles();

    List<Menu> findAllMenus();

    List<Integer> getMidsByRid(@Param("rid") Integer rid);
}
