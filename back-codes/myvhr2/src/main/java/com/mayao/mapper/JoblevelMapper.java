package com.mayao.mapper;

import com.mayao.entity.Joblevel;
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
public interface JoblevelMapper extends BaseMapper<Joblevel> {

    Integer deleteJobLevelByIds(@Param("ids") Integer[] ids);
}
