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
 * @since 2021-11-10
 */
public interface JoblevelService extends IService<Joblevel> {

    List<Joblevel> findAllJobLevels();

    int addJobLevel(Joblevel joblevel);

    int updateJobLevel(Joblevel joblevel);

    int deleteJobLevelById(Integer id);

    int deleteJobLevelByIds(Integer[] ids);


}
