package com.mayao.service;

import com.mayao.entity.Joblevel;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-25
 */
public interface JoblevelService extends IService<Joblevel> {

    List<Joblevel> findAllJobLevel();

    Integer addJobLevel(Joblevel joblevel);

    Integer updateJobLevel(Joblevel joblevel);

    Integer deleteJobLevelById(Integer id);

    Integer deleteJobLevelByIds(Integer[] ids);
}
