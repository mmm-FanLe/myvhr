package com.mayao.service;

import com.mayao.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-25
 */
public interface MenuService extends IService<Menu> {

    List<Menu> getMenusByHrId();

    List<Menu> getMenusWithRoles();

    List<Menu> findAllMenus();

    List<Integer> getMidsByRid(Integer rid);

    Boolean updateMidsByRid(Integer[] mids, Integer rid);
}
