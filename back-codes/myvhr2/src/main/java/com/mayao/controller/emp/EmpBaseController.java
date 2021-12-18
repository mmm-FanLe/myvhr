package com.mayao.controller.emp;

import com.mayao.entity.*;
import com.mayao.service.*;
import com.mayao.utils.POIUtils2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mayao on 2021/12/8 9:10 上午
 */
@RestController
@RequestMapping("/employee/basic")
public class EmpBaseController {

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

    @Autowired
    private DepartmentService departmentService;


    @GetMapping("/")
    public RespPageBean getEmpByPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                     @RequestParam(defaultValue = "10") Integer pageSize,
                                     @RequestParam(defaultValue = "")String keyword){
        return employeeService.getEmpByPage(pageNum,pageSize,keyword);
    }

    @GetMapping("/advanced")
    public RespPageBean getEmpByPageAdvanced(@RequestParam(defaultValue = "1") Integer pageNum,
                                     @RequestParam(defaultValue = "10") Integer pageSize,
                                     Employee employee,String[] beginDateScope){
        System.out.println("employee = " + employee);
        System.out.println(Arrays.toString(beginDateScope));
        return employeeService.getEmpByPageAdvanced(pageNum,pageSize,employee,beginDateScope);
    }

    @PostMapping("/")
    public RespBean addEmp(@RequestBody Employee employee){
        if (employeeService.addEmp(employee) == 1){
            return RespBean.ok("添加员工成功");
        }
        return RespBean.error("添加员工失败");
    }

    @GetMapping("/nation")
    public List<Nation> findAllNation(){
        return nationService.list();
    }

    @GetMapping("/politic")
    public List<Politicsstatus> findAllPolitic(){
        return politicsstatusService.list();
    }

    @GetMapping("/joblevel")
    public List<Joblevel> findAllJoblevel(){
        return joblevelService.list();
    }

    @GetMapping("/position")
    public List<Position> findAllPosition(){
        return positionService.list();
    }


    //- 得到员工的最大工号
    @GetMapping("/maxWorkId")
    public RespBean maxWorkId(){
        return RespBean.ok("",String.format("%08d",employeeService.maxWorkId()+1));
    }

    @DeleteMapping("/{id}")
    public RespBean deleteEmpById(@PathVariable("id")Integer id){
        if (employeeService.deleteEmpById(id) == 1){
            return RespBean.ok("删除员工成功");
        }
        return RespBean.error("删除员工失败");
    }

    @PutMapping("/")
    public RespBean updateEmp(@RequestBody Employee employee){
        if (employeeService.updateEmp(employee) == 1){
            return RespBean.ok("修改员工信息成功");
        }
        return RespBean.error("修改员工信息失败");
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportData(){
        //- 查询所有员工的信息，包括nationName等，而这些关联信息数据库(只有nationId)中没有，
        //- 故需要调用封装过resultMap的方法，而不是employeeService.list()
        List<Employee> list = (List<Employee>) employeeService.getEmpByPage(null, null, null).getData();
        return POIUtils2.empToExcel(list);
    }

    @PostMapping("/import")
    public RespBean importData(MultipartFile file) throws IOException {
        //- 解析Excel文件，excel中nation是显示的name而在数据库中nation是用的id表示，故需要对这几个字段做特殊处理
        List<Employee> list = POIUtils2.excelToEmp(file,nationService.list(),politicsstatusService.list()
        ,departmentService.list(),positionService.list(),joblevelService.list());
        //- 在数据库中插入数据
        if (employeeService.addEmps(list) == list.size()){
            return RespBean.ok("数据导入成功");
        }
        //list.forEach(System.out::println);
        return RespBean.error("数据导入失败");
    }







}
