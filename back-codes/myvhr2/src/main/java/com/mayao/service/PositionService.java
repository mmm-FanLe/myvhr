package com.mayao.service;

import com.mayao.entity.Position;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mayao.entity.RespBean;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-25
 */
public interface PositionService extends IService<Position> {

    List<Position> findAllPosition();

    Integer addPosition(Position position);

    Integer updatePosition(Position position);

    Integer deletePositionById(Integer id);

    Integer deletePositionByIds(Integer[] ids);
}
