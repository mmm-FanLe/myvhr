package com.mayao.controller.system.basic;

import com.mayao.entity.Joblevel;
import com.mayao.entity.RespBean;
import com.mayao.service.JoblevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by mayao on 2021/11/16 4:16 下午
 */
@RestController
@RequestMapping("/system/basic/joblevel")
public class JobLevelController {

    @Autowired
    private JoblevelService joblevelService;

    @GetMapping("/")
    public List<Joblevel> findAllJobLevels(){
        return joblevelService.findAllJobLevels();
    }

    @PostMapping("/")
    public RespBean addJobLevel(@RequestBody Joblevel joblevel){
        if (joblevelService.addJobLevel(joblevel) == 1){
            return RespBean.ok("添加成功");
        }
        return RespBean.error("添加失败");
    }


    @PutMapping("/")
    public RespBean updateJobLevel(@RequestBody Joblevel joblevel){
        if (joblevelService.updateJobLevel(joblevel) == 1){
            return RespBean.ok("修改成功");
        }
        return RespBean.error("修改失败");
    }

    @DeleteMapping("/{id}")
    public RespBean deleteJobLevelById(@PathVariable("id") Integer id){
        if (joblevelService.deleteJobLevelById(id) == 1){
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @DeleteMapping("/")
    public RespBean deleteJobLevelByIds(Integer[] ids){
        if (joblevelService.deleteJobLevelByIds(ids) == ids.length){
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }




}
