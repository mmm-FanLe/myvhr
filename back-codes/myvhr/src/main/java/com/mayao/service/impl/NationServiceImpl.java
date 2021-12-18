package com.mayao.service.impl;

import com.mayao.entity.Nation;
import com.mayao.mapper.NationMapper;
import com.mayao.service.NationService;
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
public class NationServiceImpl extends ServiceImpl<NationMapper, Nation> implements NationService {

    @Autowired
    private NationMapper nationMapper;

    @Override
    public List<Nation> getAllNations() {
        return nationMapper.selectList(null);
    }
}
