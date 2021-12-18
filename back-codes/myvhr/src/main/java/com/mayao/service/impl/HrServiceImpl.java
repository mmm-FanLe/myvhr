package com.mayao.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mayao.entity.Hr;
import com.mayao.entity.HrRole;
import com.mayao.entity.MenuRole;
import com.mayao.entity.vo.HrVO;
import com.mayao.mapper.HrMapper;
import com.mayao.mapper.HrRoleMapper;
import com.mayao.mapper.MenuRoleMapper;
import com.mayao.service.HrService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mayao.utils.HrUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @since 2021-11-10
 */
@Service
public class HrServiceImpl extends ServiceImpl<HrMapper, Hr> implements HrService {

    @Autowired
    private HrMapper hrMapper;

    @Autowired
    private HrRoleMapper hrRoleMapper;

    @Autowired
    private MenuRoleMapper menuRoleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Hr hr = hrMapper.loadUserByUsername(username);
        if (hr == null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        return hr;
    }

    @Override
    public List<HrVO> getAllHr() {
        return hrMapper.getAllHrs(HrUtils.getCurrentHr().getId());
    }

    @Override
    public Integer updateHr(Hr hr) {
        int i = hrMapper.updateById(hr);
        return i;
    }

    @Override
    public Boolean updateHrRole(Integer hrId, Integer[] rids) {
        //删除用户所具有的的角色
        QueryWrapper<HrRole> qw = new QueryWrapper<>();
        qw.eq("hrid",hrId);
        hrRoleMapper.delete(qw);
        //添加用户新的角色
        return hrRoleMapper.addRolesByHrId(hrId,rids) == rids.length;
    }

    @Override
    public List<HrVO> getAllHrByKeyword(String keyword) {
        return hrMapper.getAllHrsByKeyword(keyword);
    }

    @Override
    public Integer deleteHrById(Integer id) {
        //1.先删除该用户所具有的角色
        QueryWrapper<HrRole> qw = new QueryWrapper<>();
        qw.eq("hrid",id);
        hrRoleMapper.delete(qw);

        //2.再删除该用户
        QueryWrapper<Hr> qw1 = new QueryWrapper<>();
        qw1.eq("id",id);
        return hrMapper.delete(qw1);
    }
}
