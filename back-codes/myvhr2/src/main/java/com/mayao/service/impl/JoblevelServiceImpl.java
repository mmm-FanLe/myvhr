package com.mayao.service.impl;

import com.mayao.entity.Joblevel;
import com.mayao.mapper.JoblevelMapper;
import com.mayao.service.JoblevelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
public class JoblevelServiceImpl extends ServiceImpl<JoblevelMapper, Joblevel> implements JoblevelService {

    @Autowired
    private JoblevelMapper joblevelMapper;

    @Override
    public List<Joblevel> findAllJobLevel() {
        return joblevelMapper.selectList(null);
    }

    @Override
    public Integer addJobLevel(Joblevel joblevel) {
        joblevel.setEnabled(true).setCreateDate(LocalDateTime.now());
        return joblevelMapper.insert(joblevel);
    }

    @Override
    public Integer updateJobLevel(Joblevel joblevel) {
        return joblevelMapper.updateById(joblevel);
    }

    @Override
    public Integer deleteJobLevelById(Integer id) {
        return joblevelMapper.deleteById(id);
    }

    @Override
    public Integer deleteJobLevelByIds(Integer[] ids) {
        return joblevelMapper.deleteJobLevelByIds(ids);
    }
}
