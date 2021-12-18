package com.mayao.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mayao.entity.Position;
import com.mayao.entity.RespBean;
import com.mayao.mapper.PositionMapper;
import com.mayao.service.PositionService;
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
public class PositionServiceImpl extends ServiceImpl<PositionMapper, Position> implements PositionService {

    @Autowired
    private PositionMapper positionMapper;

    @Override
    public List<Position> getAllPosition() {
        return positionMapper.selectList(null);
    }

    @Override
    public int addPosition(Position position) {
        position.setCreateDate(new Date());
        position.setEnabled(true);

        return positionMapper.insert(position);
    }

    @Override
    public int updatePosition(Position position) {
        QueryWrapper<Position> qw = new QueryWrapper<>();
        qw.eq("id",position.getId());
        return positionMapper.update(position,qw);
    }

    @Override
    public int deletePosition(Integer id) {
        return positionMapper.deleteById(id);
    }

    @Override
    public int deletePositionByIds(Integer[] ids) {
        return positionMapper.deleteBatchIds(Arrays.asList(ids));
    }


}
