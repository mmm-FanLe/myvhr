package com.mayao.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mayao.entity.Hr;
import com.mayao.entity.HrRole;
import com.mayao.entity.vo.HrVO;
import com.mayao.mapper.HrMapper;
import com.mayao.mapper.HrRoleMapper;
import com.mayao.service.HrService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
public class HrServiceImpl extends ServiceImpl<HrMapper, Hr> implements HrService {

    @Autowired
    private HrMapper hrMapper;

    @Autowired
    private HrRoleMapper hrRoleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Hr hr = hrMapper.loadUserByUsername(username);
        if (hr == null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        return hr;
    }

    @Override
    public List<HrVO> findAllHrExceptCurrentUser() {
        Integer currentUserId = ((Hr) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();


        return hrMapper.findAllHrExceptCurrentUser(currentUserId);
    }

    @Override
    public List<Hr> findHrByName(String keyword) {
        return hrMapper.findHrByName(keyword);
    }

    @Override
    public Integer updateHr(Hr hr) {
        return hrMapper.updateById(hr);
    }

    @Override
    public Integer deleteHrById(Integer id) {
        return hrMapper.deleteById(id);
    }

    @Override
    public Integer updateRolesOfHrByRids(Integer hrId, Integer[] rids) {
        //1.先删除该用户拥有的所有角色
        QueryWrapper<HrRole> qw = new QueryWrapper<>();
        qw.eq("hrid",hrId);
        int delete = hrRoleMapper.delete(qw);
        if (delete == -1){
            return delete;
        }
        //2.再添加该用户的新角色

        return hrRoleMapper.addRolesWithHrid(hrId,rids);
    }
}
