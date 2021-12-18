package com.mayao.mapper;

import com.mayao.entity.Hr;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mayao.entity.vo.HrVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2021-11-10
 */
@Mapper
public interface HrMapper extends BaseMapper<Hr> {


    Hr loadUserByUsername(String username);

    List<HrVO> getAllHrs(Integer hrId);

    Integer updateHr(Hr hr);

    List<HrVO> getAllHrsByKeyword(@Param("keyword") String keyword);
}
