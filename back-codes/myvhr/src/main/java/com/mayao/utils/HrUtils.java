package com.mayao.utils;

import com.mayao.entity.Hr;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by mayao on 2021/11/21 2:52 下午
 */
public class HrUtils {

    public static Hr getCurrentHr(){
        return ((Hr) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
