package com.kejin.excelutils.controller;

import com.kejin.excelutils.entity.Student;
import com.kejin.excelutils.utils.ExcelTempUtil;
import com.kejin.excelutils.utils.ExcelUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/exceltest")
public class TestController {

    @Value("${excel.templeDir}")
    private String templeDir;

    @RequestMapping("/get/excel")
    public void getExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 准备数据
        List<Student> list = new ArrayList<>();
        for (int i = 0; i < 50000; i++) {
            list.add(new Student(111,"王五"+i,"女"));
        }

        //excel表头
        String[] columnNames = { "ID", "姓名", "性别" };

        //字段名称
        String[] fieldStr = { "id", "name", "sex" };

        //文件名
        String fileName="导出数据";

        ExcelUtil util = new ExcelUtil();
        util.exportExcel(columnNames, fieldStr,list, fileName, ExcelUtil.EXCEL_FILE_2003,request,response);
    }

    @RequestMapping("/get/getExcelTemp")
    public void getExcelTemp(HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<Student> list = new ArrayList<>();
        for (int i = 0; i < 80000; i++) {
            list.add(new Student(111, "王五" + i, "女"));
        }

        //模板内容实例
        /*        ID	姓名	性别
                <jx:forEach items="${items}" var="item" >
                ${item.id} 	${item.name}	${item.sex}
                </jx:forEach>
        */

        //文件名
        String fileName="导出数据";

        ExcelTempUtil util = new ExcelTempUtil();
        util.createExcel(templeDir+"exportExc.xlsx",list,fileName,request,response);

    }

}