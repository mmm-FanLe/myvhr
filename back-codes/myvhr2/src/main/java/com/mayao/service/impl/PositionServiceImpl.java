package com.mayao.service.impl;

import com.mayao.entity.Position;
import com.mayao.entity.RespBean;
import com.mayao.mapper.PositionMapper;
import com.mayao.service.PositionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
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
@Transactional
public class PositionServiceImpl extends ServiceImpl<PositionMapper, Position> implements PositionService {

    @Autowired
    private PositionMapper positionMapper;


    @Override
    public List<Position> findAllPosition() {
        return positionMapper.selectList(null);
    }

    @Override
    public Integer addPosition(Position position) {
        position.setEnabled(true);
        position.setCreateDate(LocalDateTime.now());
        return positionMapper.insert(position);
    }

    @Override
    public Integer updatePosition(Position position) {
        return positionMapper.updateById(position);
    }

    @Override
    public Integer deletePositionById(Integer id) {
        return positionMapper.deleteById(id);
    }

    @Override
    public Integer deletePositionByIds(Integer[] ids) {
        //return positionMapper.deleteBatchIds(Arrays.asList(ids));
        return positionMapper.deletePositionByIds(ids);
    }



}
