package com.mayao.service;

import com.mayao.entity.Role;
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
public interface RoleService extends IService<Role> {

    List<Role> findAllRole();

    Integer addRole(Role role);

    Integer deleteRoleById(Integer id);
}
