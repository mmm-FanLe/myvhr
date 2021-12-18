package com.mayao.service.impl;

import com.mayao.entity.Politicsstatus;
import com.mayao.mapper.PoliticsstatusMapper;
import com.mayao.service.PoliticsstatusService;
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
 * @since 2021-11-10
 */
@Service
public class PoliticsstatusServiceImpl extends ServiceImpl<PoliticsstatusMapper, Politicsstatus> implements PoliticsstatusService {

    @Autowired
    private PoliticsstatusMapper politicsstatusMapper;

    @Override
    public List<Politicsstatus> getAllPoliticsstatus() {
        return politicsstatusMapper.selectList(null);
    }
}
