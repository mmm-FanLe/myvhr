package com.mayao.mapper;

import com.mayao.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mayao.entity.vo.MenuVO;
import com.mayao.entity.vo.MenuVO2;
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
public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> getMenusByHrId(Integer id);

    List<MenuVO2> getAllMenusWithRole();

    List<MenuVO> FindAllMenus();
}
