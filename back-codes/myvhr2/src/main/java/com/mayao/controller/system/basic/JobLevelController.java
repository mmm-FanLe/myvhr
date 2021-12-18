package com.mayao.controller.system.basic;

import com.mayao.entity.Joblevel;
import com.mayao.entity.RespBean;
import com.mayao.service.JoblevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by mayao on 2021/12/3 3:19 下午
 */
@RestController
@RequestMapping("/system/basic/joblevel")
public class JobLevelController {

    @Autowired
    private JoblevelService joblevelService;

    @GetMapping("/")
    public List<Joblevel> findAllJobLevel(){
        return joblevelService.findAllJobLevel();
    }

    @PostMapping("/")
    public RespBean addJobLevel(@RequestBody Joblevel joblevel){
        if (joblevelService.addJobLevel(joblevel) == 1){
            return RespBean.ok("添加职称成功");
        }
        return RespBean.error("添加职称失败");
    }

    @PutMapping("/")
    public RespBean updateJobLevel(@RequestBody Joblevel joblevel){
        if (joblevelService.updateJobLevel(joblevel) == 1){
            return RespBean.ok("修改职称成功");
        }
        return RespBean.error("修改职称成功");
    }

    @DeleteMapping("/{id}")
    public RespBean deleteJobLevelById(@PathVariable("id") Integer id){
        if (joblevelService.deleteJobLevelById(id) == 1){
            return RespBean.ok("删除职称成功");
        }
        return RespBean.error("删除职称失败");
    }

    @DeleteMapping("/")
    public RespBean deleteJobLevelByIds(Integer[] ids){
        if (joblevelService.deleteJobLevelByIds(ids) == ids.length){
            return RespBean.ok("批量删除职称成功");
        }
        return RespBean.error("批量删除职称失败");
    }
}
