package com.mayao.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mayao.entity.MenuRole;
import com.mayao.entity.Role;
import com.mayao.mapper.MenuRoleMapper;
import com.mayao.mapper.RoleMapper;
import com.mayao.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-25
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private MenuRoleMapper menuRoleMapper;

    @Override
    public List<Role> findAllRole() {
        return roleMapper.selectList(null);
    }

    @Override
    public Integer addRole(Role role) {
        if (!role.getName().startsWith("ROLE_")){
            role.setName("ROLE_"+role.getName());
        }
        return roleMapper.insert(role);
    }

    @Override
    public Integer deleteRoleById(Integer id) {
        //1.删除角色信息前，先删除角色权限关联表中的数据
        QueryWrapper<MenuRole> qw=  new QueryWrapper<>();
        qw.eq("rid",id);
        menuRoleMapper.delete(qw);
        //2.再删除角色
        return roleMapper.deleteById(id);
    }
}
