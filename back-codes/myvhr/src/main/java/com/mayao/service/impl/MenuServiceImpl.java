package com.mayao.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mayao.entity.Hr;
import com.mayao.entity.Menu;
import com.mayao.entity.MenuRole;
import com.mayao.entity.vo.MenuVO;
import com.mayao.entity.vo.MenuVO2;
import com.mayao.mapper.MenuMapper;
import com.mayao.mapper.MenuRoleMapper;
import com.mayao.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-10
 */
@Service
@Transactional
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private MenuRoleMapper menuRoleMapper;

    @Override
    public List<Menu> getMenusByHrId() {

        List<Menu> menusByHrId = menuMapper.getMenusByHrId(((Hr) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
        return menusByHrId;
    }


    @Override
    public List<MenuVO2> getAllMenusWithRole() {
        return menuMapper.getAllMenusWithRole();
    }

    @Override
    public List<MenuVO> FindAllMenus() {
        return menuMapper.FindAllMenus();
    }

    @Override
    public List<Integer> getMidsByRid(Integer rid) {

        List<Integer> list = new ArrayList<>();
        QueryWrapper<MenuRole> qw = new QueryWrapper<>();
        qw.eq("rid",rid);
        List<MenuRole> menuRoles = menuRoleMapper.selectList(qw);
        for (MenuRole menuRole : menuRoles) {
            list.add(menuRole.getMid());
        }
        return list;
    }

    @Override
    public boolean updateMidsByRid(Integer[] mids, Integer rid) {

        //1.根据rid把角色对应的权限删除
        QueryWrapper<MenuRole> qw = new QueryWrapper<>();
        qw.eq("rid",rid);
        menuRoleMapper.delete(qw);
        //2.再根据rid将mids插入到数据库中
        if (mids == null || mids.length == 0){//mids为null代表删除所有权限
            return true;
        }
        Integer result = menuRoleMapper.insertMidsAndRid(mids, rid);
        return result== mids.length;
    }
}
