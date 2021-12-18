package com.mayao;

import com.mayao.mapper.*;
import com.mayao.service.MenuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MyvhrApplicationTests {

    @Autowired
    private HrMapper hrMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PositionMapper positionMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private MenuService menuService;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Test
    void contextLoads() {
        //System.out.println(hrMapper.loadUserByUsername("zenggong"));
        //System.out.println(roleMapper.selectRolesByHrId(3));
        //positionMapper.selectList(null).forEach(System.out::println);
        /*for (int i = 0; i < menuMapper.FindAllMenus().size(); i++) {
            System.out.println(i+"==="+menuMapper.FindAllMenus().get(i));
        }*/
        //menuService.getMidsByRid(6).forEach(System.out::println);
        //departmentMapper.getAllDepartmentsByParentId(-1).forEach(System.out::println);
       //hrMapper.getAllHrs(3).forEach(System.out::println);
        //System.out.println(hrMapper.getAllHrs(3).size());
        //hrMapper.getAllHrsByKeyword("李").forEach(System.out::println);
        //employeeMapper.getEmployeesByPage(0,2).forEach(System.out::println);
        menuMapper.getAllMenusWithRole().forEach(System.out::println);
    }






}
