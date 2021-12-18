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
 * @since 2021-11-25
 */
public interface HrService extends IService<Hr>, UserDetailsService {

    List<HrVO> findAllHrExceptCurrentUser();

    List<Hr> findHrByName(String keyword);

    Integer updateHr(Hr hr);

    Integer deleteHrById(Integer id);

    Integer updateRolesOfHrByRids(Integer hrId, Integer[] rids);
}
