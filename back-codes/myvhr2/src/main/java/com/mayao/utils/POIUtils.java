package com.mayao.utils;

import com.mayao.entity.Employee;
import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * ! 生成xls文件
 * Created by mayao on 2021/12/12 2:57 下午
 */
public class POIUtils {

    public static ResponseEntity<byte[]> empToExcel(List<Employee> list) {
        //1.创建一个Excel文档
        HSSFWorkbook workbook = new HSSFWorkbook();
        //2.创建文档摘要
        workbook.createInformationProperties();
        //3.配置文档摘要信息
        DocumentSummaryInformation docInfo = workbook.getDocumentSummaryInformation();
        //文档类别
        docInfo.setCategory("员工信息");
        //文档管理员
        docInfo.setManager("马遥");
        //公司信息
        docInfo.setCompany("马马马有限公司");
        //4.配置文档信息
        SummaryInformation Info = workbook.getSummaryInformation();
        //文档标题
        Info.setTitle("员工信息表");//? excel左下角的sheet名称
        //作者
        Info.setAuthor("马凡乐");
        //文档备注
        Info.setComments("本文档由mayao提供");
        //5.创建表格样式
        HSSFCellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setFillForegroundColor(IndexedColors.YELLOW.index);//背景颜色
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);//填充模式
        HSSFCellStyle dateStyle = workbook.createCellStyle();
        dateStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/y"));
        //6.创建表格
        HSSFSheet sheet = workbook.createSheet("员工信息表");
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
        //创建标题行
        HSSFRow titleRow = sheet.createRow(0);
        HSSFCell c0 = titleRow.createCell(0);
        c0.setCellValue("编号");
        c0.setCellStyle(titleStyle);
        HSSFCell c1 = titleRow.createCell(1);
        c1.setCellStyle(titleStyle);
        c1.setCellValue("姓名");
        HSSFCell c2 = titleRow.createCell(2);
        c2.setCellStyle(titleStyle);
        c2.setCellValue("工号");
        HSSFCell c3 = titleRow.createCell(3);
        c3.setCellStyle(titleStyle);
        c3.setCellValue("性别");
        HSSFCell c4 = titleRow.createCell(4);
        c4.setCellStyle(titleStyle);
        c4.setCellValue("出生日期");
        HSSFCell c5 = titleRow.createCell(5);
        c5.setCellStyle(titleStyle);
        c5.setCellValue("身份证号码");
        HSSFCell c6 = titleRow.createCell(6);
        c6.setCellStyle(titleStyle);
        c6.setCellValue("婚姻状况");
        HSSFCell c7 = titleRow.createCell(7);
        c7.setCellStyle(titleStyle);
        c7.setCellValue("民族");
        HSSFCell c8 = titleRow.createCell(8);
        c8.setCellStyle(titleStyle);
        c8.setCellValue("籍贯");
        HSSFCell c9 = titleRow.createCell(9);
        c9.setCellStyle(titleStyle);
        c9.setCellValue("政治面貌");
        HSSFCell c10 = titleRow.createCell(10);
        c10.setCellStyle(titleStyle);
        c10.setCellValue("电话号码");
        HSSFCell c11 = titleRow.createCell(11);
        c11.setCellStyle(titleStyle);
        c11.setCellValue("联系地址");
        HSSFCell c12 = titleRow.createCell(12);
        c12.setCellStyle(titleStyle);
        c12.setCellValue("所属部门");
        HSSFCell c13 = titleRow.createCell(13);
        c13.setCellStyle(titleStyle);
        c13.setCellValue("职称");
        HSSFCell c14 = titleRow.createCell(14);
        c14.setCellStyle(titleStyle);
        c14.setCellValue("职位");
        HSSFCell c15 = titleRow.createCell(15);
        c15.setCellStyle(titleStyle);
        c15.setCellValue("聘用形式");
        HSSFCell c16 = titleRow.createCell(16);
        c16.setCellStyle(titleStyle);
        c16.setCellValue("最高学历");
        HSSFCell c17 = titleRow.createCell(17);
        c17.setCellStyle(titleStyle);
        c17.setCellValue("专业");
        HSSFCell c18 = titleRow.createCell(18);
        c18.setCellStyle(titleStyle);
        c18.setCellValue("毕业院校");
        HSSFCell c19 = titleRow.createCell(19);
        c19.setCellStyle(titleStyle);
        c19.setCellValue("入职日期");
        HSSFCell c20 = titleRow.createCell(20);
        c20.setCellStyle(titleStyle);
        c20.setCellValue("在职状态");
        HSSFCell c21 = titleRow.createCell(21);
        c21.setCellStyle(titleStyle);
        c21.setCellValue("邮箱");
        HSSFCell c22 = titleRow.createCell(22);
        c22.setCellStyle(titleStyle);
        c22.setCellValue("合同期限(年)");
        HSSFCell c23 = titleRow.createCell(23);
        c23.setCellStyle(titleStyle);
        c23.setCellValue("合同起始日期");
        HSSFCell c24 = titleRow.createCell(24);
        c24.setCellStyle(titleStyle);
        c24.setCellValue("合同终止日期");
        //- 实体类中的date属性是Date类型则不需要格式化，而LocalDate类型必须格式化
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (int i = 0; i < list.size(); i++) {
            Employee emp = list.get(i);
            HSSFRow row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(emp.getId());
            row.createCell(1).setCellValue(emp.getName());
            row.createCell(2).setCellValue(emp.getWorkID());
            row.createCell(3).setCellValue(emp.getGender());

            HSSFCell cell4 = row.createCell(4);
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

            HSSFCell cell19 = row.createCell(19);
            cell19.setCellValue(formatter.format(emp.getBeginDate()));
            cell19.setCellStyle(dateStyle);

            row.createCell(20).setCellValue(emp.getWorkState());
            row.createCell(21).setCellValue(emp.getEmail());
            row.createCell(22).setCellValue(emp.getContractTerm());

            HSSFCell cell23 = row.createCell(23);
            cell23.setCellValue(formatter.format(emp.getBeginContract()));
            cell23.setCellStyle(dateStyle);
            HSSFCell cell24 = row.createCell(24);
            cell24.setCellValue(formatter.format(emp.getEndContract()));
            cell24.setCellStyle(dateStyle);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        HttpHeaders headers = new HttpHeaders();
        try {
            headers.setContentDispositionFormData("attachment", new String("员工表.xls".getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            workbook.write(baos);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(baos.toByteArray(), headers, HttpStatus.CREATED);
    }
}
