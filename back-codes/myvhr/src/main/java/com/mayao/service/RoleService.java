package com.mayao.service;

import com.mayao.entity.Menu;
import com.mayao.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mayao.entity.vo.MenuVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-10
 */
public interface RoleService extends IService<Role> {

    List<Role> findAllRole();

    List<MenuVO> FindAllMenus();

    Integer addRole(Role role);

    Integer delRoleById(Integer rid);
}
