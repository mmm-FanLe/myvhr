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
 * @since 2021-11-10
 */
public interface PositionService extends IService<Position> {


    List<Position> getAllPosition();

    int addPosition(Position position);

    int updatePosition(Position position);

    int deletePosition(Integer id);

    int deletePositionByIds(Integer[] ids);
}
