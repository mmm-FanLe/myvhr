package com.mayao.service;

import com.mayao.entity.Hr;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mayao.entity.vo.HrVO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-10
 */
public interface HrService extends IService<Hr>, UserDetailsService {

    List<HrVO> getAllHr();

    Integer updateHr(Hr hr);

    Boolean updateHrRole(Integer hrId, Integer[] rids);

    List<HrVO> getAllHrByKeyword(String keyword);

    Integer deleteHrById(Integer id);
}
