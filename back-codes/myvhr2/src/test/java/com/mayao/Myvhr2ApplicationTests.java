package com.mayao;

import com.mayao.mapper.HrMapper;
import com.mayao.mapper.MenuMapper;
import com.mayao.mapper.MenuRoleMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Myvhr2ApplicationTests {

    @Autowired
    private HrMapper hrMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private MenuRoleMapper menuRoleMapper;

    @Test
    public void test(){
        //System.out.println(hrMapper.loadUserByUsername("hanyu"));
        //menuMapper.getMenusWithRoles().forEach(System.out::println);
        //menuMapper.findAllMenus().forEach(System.out::println);
        menuRoleMapper.addMidsByRid(new Integer[]{7,8,9},21);
    }

}
