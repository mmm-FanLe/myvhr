package com.mayao.mapper;

import com.mayao.entity.Hr;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mayao.entity.vo.HrVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2021-11-25
 */
@Mapper
public interface HrMapper extends BaseMapper<Hr> {

    Hr loadUserByUsername(String username);

    List<Hr> findHrByName(String keyword);

    List<HrVO> findAllHrExceptCurrentUser(Integer id);
}
