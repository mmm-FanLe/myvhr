package com.mayao.mapper;

import com.mayao.entity.Position;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2021-11-25
 */
@Mapper
public interface PositionMapper extends BaseMapper<Position> {

    Integer deletePositionByIds(@Param("ids") Integer[] ids);
}
