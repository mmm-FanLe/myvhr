package com.mayao.service;

import com.mayao.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mayao.entity.vo.MenuVO;
import com.mayao.entity.vo.MenuVO2;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-10
 */
public interface MenuService extends IService<Menu> {
    public List<Menu> getMenusByHrId();

    //@Cacheable //将该方法得到的值缓存
    public List<MenuVO2> getAllMenusWithRole();

    List<MenuVO> FindAllMenus();

    List<Integer> getMidsByRid(Integer rid);

    boolean updateMidsByRid(Integer[] mids, Integer rid);
}
