package com.mayao.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mayao.entity.Joblevel;
import com.mayao.mapper.JoblevelMapper;
import com.mayao.service.JoblevelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
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
public class JoblevelServiceImpl extends ServiceImpl<JoblevelMapper, Joblevel> implements JoblevelService {

    @Autowired
    private JoblevelMapper joblevelMapper;

    @Override
    public List<Joblevel> findAllJobLevels() {
        return joblevelMapper.selectList(null);
    }

    @Override
    public int addJobLevel(Joblevel joblevel) {
        joblevel.setCreateDate(new Date());
        joblevel.setEnabled(true);
        return joblevelMapper.insert(joblevel);
    }

    @Override
    public int updateJobLevel(Joblevel joblevel) {
        QueryWrapper<Joblevel> qw = new QueryWrapper<>();
        qw.eq("id",joblevel.getId());
        return joblevelMapper.update(joblevel,qw);
    }

    @Override
    public int deleteJobLevelById(Integer id) {
        return joblevelMapper.deleteById(id);
    }

    @Override
    public int deleteJobLevelByIds(Integer[] ids) {
        return joblevelMapper.deleteBatchIds(Arrays.asList(ids));
    }



}
