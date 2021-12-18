package com.mayao.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mayao.entity.Hr;
import com.mayao.entity.Menu;
import com.mayao.entity.MenuRole;
import com.mayao.mapper.MenuMapper;
import com.mayao.mapper.MenuRoleMapper;
import com.mayao.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-25
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
        return menuMapper.getMenusByHrId(((Hr) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
    }

    @Override
    public List<Menu> getMenusWithRoles() {
        return menuMapper.getMenusWithRoles();
    }

    @Override
    public List<Menu> findAllMenus() {
        return menuMapper.findAllMenus();
    }

    @Override
    public List<Integer> getMidsByRid(Integer rid) {
        return menuMapper.getMidsByRid(rid);
    }

    @Override
    public Boolean updateMidsByRid(Integer[] mids, Integer rid) {
        //1.首先把该角色对应的所有权限都删除
        QueryWrapper<MenuRole> qw = new QueryWrapper<>();
        qw.eq("rid",rid);
        menuRoleMapper.delete(qw);
        //说明要删除这个角色的全部权限
        if (mids == null || mids.length == 0) {
            return true;
        }
        //2.给该角色添加新的权限
        return mids.length == menuRoleMapper.addMidsByRid(mids,rid);
    }
}
