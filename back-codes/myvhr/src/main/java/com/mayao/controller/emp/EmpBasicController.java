package com.mayao.controller.emp;

import com.alibaba.druid.sql.visitor.functions.If;
import com.mayao.entity.*;
import com.mayao.entity.vo.EmployeeVO;
import com.mayao.entity.vo.RespPageBean;
import com.mayao.service.*;
import com.mayao.utils.POIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by mayao on 2021/11/22 9:54 上午
 */
@RestController
@RequestMapping("/employee/basic")
public class EmpBasicController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private NationService nationService;

    @Autowired
    private PoliticsstatusService politicsstatusService;

    @Autowired
    private JoblevelService joblevelService;

    @Autowired
    private PositionService positionService;

    @GetMapping("/")
    public RespPageBean getEmployeesByPage(@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "10") Integer pageSize,@RequestParam(defaultValue = "") String keyword){
        return employeeService.getEmployeesByPage(pageNum,pageSize,keyword);
    }

    /**
     * @param employee
     * @return 返回1表示添加成功，返回2表示添加失败
     */
    @PostMapping("/")
    public RespBean addEmp(@RequestBody Employee employee){
        if (employeeService.addEmp(employee) == 1){
            return RespBean.ok("添加成功");
        }
        return RespBean.error("添加失败");
    }

    @GetMapping("/nations")
    public List<Nation> getAllNations(){
        return nationService.getAllNations();
    }

    @GetMapping("/politics")
    public List<Politicsstatus> getAllPoliticsstatus(){
        return politicsstatusService.getAllPoliticsstatus();
    }

    @GetMapping("/joblevels")
    public List<Joblevel> getAllJobLevel(){
        return joblevelService.findAllJobLevels();
    }

    @GetMapping("/positions")
    public List<Position> getAllPositions(){
        return positionService.getAllPosition();
    }

    @GetMapping("/maxWorkID")
    public RespBean maxWorkID(){
        return RespBean.ok("",String.format("%08d",employeeService.maxWorkID()+1));
    }

    @DeleteMapping("/{id}")
    public RespBean deleteEmployeeById(@PathVariable("id") Integer id){
        if (employeeService.deleteEmployeeById(id) == 1){
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @PutMapping("/")
    public RespBean updateEmp(@RequestBody Employee employee){
        if (employeeService.updateEmp(employee) == 1){
            return RespBean.ok("更新成功");
        }
        return RespBean.error("更新失败");
    }


    @GetMapping("/export")
    public ResponseEntity<byte[]> exportData(){
        List<EmployeeVO> employeeVO = employeeService.findAllEmployeeVO();
        return POIUtils.employee2Excel(employeeVO);
    }

    @PostMapping("/import")
    public RespBean importData(MultipartFile file) throws IOException {
        file.transferTo(new File("/Users/mayao/Desktop/mayao.xls"));
        return RespBean.ok("上传成功");
    }


    @GetMapping("/advance")
    public List<Employee> advance(Employee employee){
       return employeeService.advance(employee);
    }

}
