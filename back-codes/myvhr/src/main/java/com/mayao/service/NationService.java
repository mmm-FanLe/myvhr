package com.mayao.service;

import com.mayao.entity.Nation;
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
public interface NationService extends IService<Nation> {

    List<Nation> getAllNations();
}
