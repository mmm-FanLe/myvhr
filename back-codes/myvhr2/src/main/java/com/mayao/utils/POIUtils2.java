package com.mayao.utils;

import com.mayao.entity.*;
import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * ! 生成xlsx文件
 * Created by mayao on 2021/12/12 2:57 下午
 */
public class POIUtils2 {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * 将emp的数据生成excel表格
     *
     * @param list
     * @return
     */
    public static ResponseEntity<byte[]> empToExcel(List<Employee> list) {
        //1.创建一个Excel文档
        XSSFWorkbook workbook = new XSSFWorkbook();
        //5.创建表格样式
        XSSFCellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setFillForegroundColor(IndexedColors.YELLOW.index);//背景颜色
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);//填充模式
        XSSFCellStyle dateStyle = workbook.createCellStyle();
        dateStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/y"));
        //6.创建表格
        XSSFSheet sheet = workbook.createSheet("员工信息表");
        //设置每列的宽度
        sheet.setColumnWidth(0, 5 * 256);
        sheet.setColumnWidth(1, 12 * 256);
        sheet.setColumnWidth(2, 10 * 256);
        sheet.setColumnWidth(3, 5 * 256);
        sheet.setColumnWidth(4, 12 * 256);
        sheet.setColumnWidth(5, 20 * 256);
        sheet.setColumnWidth(6, 10 * 256);
        sheet.setColumnWidth(7, 10 * 256);
        sheet.setColumnWidth(8, 16 * 256);
        sheet.setColumnWidth(9, 12 * 256);
        sheet.setColumnWidth(10, 15 * 256);
        sheet.setColumnWidth(11, 20 * 256);
        sheet.setColumnWidth(12, 16 * 256);
        sheet.setColumnWidth(13, 14 * 256);
        sheet.setColumnWidth(14, 14 * 256);
        sheet.setColumnWidth(15, 12 * 256);
        sheet.setColumnWidth(16, 8 * 256);
        sheet.setColumnWidth(17, 20 * 256);
        sheet.setColumnWidth(18, 20 * 256);
        sheet.setColumnWidth(19, 15 * 256);
        sheet.setColumnWidth(20, 8 * 256);
        sheet.setColumnWidth(21, 25 * 256);
        sheet.setColumnWidth(22, 14 * 256);
        sheet.setColumnWidth(23, 15 * 256);
        sheet.setColumnWidth(24, 15 * 256);
        sheet.setColumnWidth(25, 15 * 256);
        //创建标题行
        XSSFRow titleRow = sheet.createRow(0);
        XSSFCell c0 = titleRow.createCell(0);
        c0.setCellValue("编号");
        c0.setCellStyle(titleStyle);
        XSSFCell c1 = titleRow.createCell(1);
        c1.setCellStyle(titleStyle);
        c1.setCellValue("姓名");
        XSSFCell c2 = titleRow.createCell(2);
        c2.setCellStyle(titleStyle);
        c2.setCellValue("工号");
        XSSFCell c3 = titleRow.createCell(3);
        c3.setCellStyle(titleStyle);
        c3.setCellValue("性别");
        XSSFCell c4 = titleRow.createCell(4);
        c4.setCellStyle(titleStyle);
        c4.setCellValue("出生日期");
        XSSFCell c5 = titleRow.createCell(5);
        c5.setCellStyle(titleStyle);
        c5.setCellValue("身份证号码");
        XSSFCell c6 = titleRow.createCell(6);
        c6.setCellStyle(titleStyle);
        c6.setCellValue("婚姻状况");
        XSSFCell c7 = titleRow.createCell(7);
        c7.setCellStyle(titleStyle);
        c7.setCellValue("民族");
        XSSFCell c8 = titleRow.createCell(8);
        c8.setCellStyle(titleStyle);
        c8.setCellValue("籍贯");
        XSSFCell c9 = titleRow.createCell(9);
        c9.setCellStyle(titleStyle);
        c9.setCellValue("政治面貌");
        XSSFCell c10 = titleRow.createCell(10);
        c10.setCellStyle(titleStyle);
        c10.setCellValue("电话号码");
        XSSFCell c11 = titleRow.createCell(11);
        c11.setCellStyle(titleStyle);
        c11.setCellValue("联系地址");
        XSSFCell c12 = titleRow.createCell(12);
        c12.setCellStyle(titleStyle);
        c12.setCellValue("所属部门");
        XSSFCell c13 = titleRow.createCell(13);
        c13.setCellStyle(titleStyle);
        c13.setCellValue("职称");
        XSSFCell c14 = titleRow.createCell(14);
        c14.setCellStyle(titleStyle);
        c14.setCellValue("职位");
        XSSFCell c15 = titleRow.createCell(15);
        c15.setCellStyle(titleStyle);
        c15.setCellValue("聘用形式");
        XSSFCell c16 = titleRow.createCell(16);
        c16.setCellStyle(titleStyle);
        c16.setCellValue("最高学历");
        XSSFCell c17 = titleRow.createCell(17);
        c17.setCellStyle(titleStyle);
        c17.setCellValue("专业");
        XSSFCell c18 = titleRow.createCell(18);
        c18.setCellStyle(titleStyle);
        c18.setCellValue("毕业院校");
        XSSFCell c19 = titleRow.createCell(19);
        c19.setCellStyle(titleStyle);
        c19.setCellValue("入职日期");
        XSSFCell c20 = titleRow.createCell(20);
        c20.setCellStyle(titleStyle);
        c20.setCellValue("在职状态");
        XSSFCell c21 = titleRow.createCell(21);
        c21.setCellStyle(titleStyle);
        c21.setCellValue("邮箱");
        XSSFCell c22 = titleRow.createCell(22);
        c22.setCellStyle(titleStyle);
        c22.setCellValue("合同期限(年)");
        XSSFCell c23 = titleRow.createCell(23);
        c23.setCellStyle(titleStyle);
        c23.setCellValue("合同起始日期");
        XSSFCell c24 = titleRow.createCell(24);
        c24.setCellStyle(titleStyle);
        c24.setCellValue("合同终止日期");

        XSSFCell c25 = titleRow.createCell(25);
        c25.setCellValue("工龄");
        c25.setCellStyle(titleStyle);


        for (int i = 0; i < list.size(); i++) {
            Employee emp = list.get(i);
            XSSFRow row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(emp.getId());
            row.createCell(1).setCellValue(emp.getName());
            row.createCell(2).setCellValue(emp.getWorkID());
            row.createCell(3).setCellValue(emp.getGender());

            XSSFCell cell4 = row.createCell(4);
            cell4.setCellValue(formatter.format(emp.getBirthday()));
            cell4.setCellStyle(dateStyle);

            row.createCell(5).setCellValue(emp.getIdCard());
            row.createCell(6).setCellValue(emp.getWedlock());
            row.createCell(7).setCellValue(emp.getNation().getName());
            row.createCell(8).setCellValue(emp.getNativePlace());
            row.createCell(9).setCellValue(emp.getPoliticsstatus().getName());
            row.createCell(10).setCellValue(emp.getPhone());
            row.createCell(11).setCellValue(emp.getAddress());
            row.createCell(12).setCellValue(emp.getDepartment().getName());
            row.createCell(13).setCellValue(emp.getJoblevel().getName());
            row.createCell(14).setCellValue(emp.getPosition().getName());
            row.createCell(15).setCellValue(emp.getEngageForm());
            row.createCell(16).setCellValue(emp.getTiptopDegree());
            row.createCell(17).setCellValue(emp.getSpecialty());
            row.createCell(18).setCellValue(emp.getSchool());

            XSSFCell cell19 = row.createCell(19);
            cell19.setCellValue(formatter.format(emp.getBeginDate()));
            cell19.setCellStyle(dateStyle);

            row.createCell(20).setCellValue(emp.getWorkState());
            row.createCell(21).setCellValue(emp.getEmail());
            row.createCell(22).setCellValue(emp.getContractTerm());

            XSSFCell cell23 = row.createCell(23);
            cell23.setCellValue(formatter.format(emp.getBeginContract()));
            cell23.setCellStyle(dateStyle);

            XSSFCell cell24 = row.createCell(24);
            cell24.setCellValue(formatter.format(emp.getEndContract()));
            cell24.setCellStyle(dateStyle);

            row.createCell(25).setCellValue(emp.getWorkAge());
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        HttpHeaders headers = new HttpHeaders();
        try {
            headers.setContentDispositionFormData("attachment", new String("员工表.xlsx".getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            workbook.write(baos);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(baos.toByteArray(), headers, HttpStatus.CREATED);
    }


    /**
     * 将excel解析成员工集合
     *
     * @param file
     * @param nations
     * @param politics
     * @param departments
     * @param positions
     * @param jobLevels
     * @return
     */
    public static List<Employee> excelToEmp(MultipartFile file, List<Nation> nations, List<Politicsstatus> politics, List<Department> departments, List<Position> positions, List<Joblevel> jobLevels) {
        List<Employee> list = new ArrayList<>();
        try {
            //1.创建excel对象
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            //2.获取表单的数量
            int sheets = workbook.getNumberOfSheets();
            //遍历表单
            for (int i = 0; i < sheets; i++) {
                //3.获取表单
                XSSFSheet sheet = workbook.getSheetAt(i);
                //4.获取表单中的行数
                int rows = sheet.getPhysicalNumberOfRows();
                for (int j = 0; j < rows; j++) {
                    //跳过标题行
                    if (j == 0) {
                        continue;
                    }
                    //5.获取行
                    XSSFRow row = sheet.getRow(j);
                    //跳过空行
                    if (row == null) {
                        continue;
                    }

                    Employee emp = new Employee();

                    emp.setName(row.getCell(1).getStringCellValue());
                    emp.setWorkID(String.valueOf(row.getCell(2).getNumericCellValue()));
                    emp.setGender(row.getCell(3).getStringCellValue());
                    emp.setBirthday(LocalDate.parse(row.getCell(4).getStringCellValue(),formatter));
                    emp.setIdCard(row.getCell(5).getStringCellValue());
                    emp.setWedlock(row.getCell(6).getStringCellValue());
                    nations.forEach(nation -> {
                        if (nation.getName().equals(row.getCell(7).getStringCellValue())) {
                            emp.setNationId(nation.getId());
                        }
                    });
                    emp.setNativePlace(row.getCell(8).getStringCellValue());
                    politics.forEach(politic -> {
                        if (politic.getName().equals(row.getCell(9).getStringCellValue())) {
                            emp.setPoliticId(politic.getId());
                        }
                    });
                    emp.setPhone(row.getCell(10).getStringCellValue());
                    emp.setAddress(row.getCell(11).getStringCellValue());
                    departments.forEach(department -> {
                        if (department.getName().equals(row.getCell(12).getStringCellValue())) {
                            emp.setDepartmentId(department.getId());
                        }
                    });
                    jobLevels.forEach(joblevel -> {
                        if (joblevel.getName().equals(row.getCell(13).getStringCellValue())) {
                            emp.setJobLevelId(joblevel.getId());
                        }
                    });
                    positions.forEach(position -> {
                        if (position.getName().equals(row.getCell(14).getStringCellValue())) {
                            emp.setPosId(position.getId());
                        }
                    });
                    emp.setEngageForm(row.getCell(15).getStringCellValue());
                    emp.setTiptopDegree(row.getCell(16).getStringCellValue());
                    emp.setSpecialty(row.getCell(17).getStringCellValue());
                    emp.setSchool(row.getCell(18).getStringCellValue());
                    emp.setBeginDate(LocalDate.parse(row.getCell(19).getStringCellValue(),formatter));
                    emp.setWorkState(row.getCell(20).getStringCellValue());
                    emp.setEmail(row.getCell(21).getStringCellValue());
                    emp.setContractTerm(row.getCell(22).getNumericCellValue());
                    emp.setBeginContract(LocalDate.parse(row.getCell(23).getStringCellValue(),formatter));
                    emp.setEndContract(LocalDate.parse(row.getCell(24).getStringCellValue(),formatter));
                    emp.setWorkAge((int) row.getCell(25).getNumericCellValue());

                    list.add(emp);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
