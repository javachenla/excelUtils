package com.kejin.excelutils.utils;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.formula.functions.T;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author chenjian
 * @Date 2020/3/11 10:53
 **/
public class ExcelTempUtil {

    String basePath=System.getProperty("java.io.tmpdir")+"excelTemple/";


    /**
     * 根据模板生成Excel文件.
     * @param templateFileName 模板文件.
     * @param list 模板中存放的数据.
     * @param resultFileName 生成的文件.
     */
    public  void createExcelByo(String templateFileName, List<?> list, String resultFileName){
        //创建XLSTransformer对象
        XLSTransformer transformer = new XLSTransformer();
        //获取java项目编译后根路径
        //URL url = this.getClass().getClassLoader().getResource("");
        //得到模板文件路径
        //String srcFilePath = url.getPath() + templateFileName;
        String srcFilePath =templateFileName;
        Map<String,Object> beanParams = new HashMap<String,Object>();
        beanParams.put("items", list);
        //String destFilePath = url.getPath() + resultFileName;
        String destFilePath =  resultFileName;
        try {
            //生成Excel文件
            transformer.transformXLS(srcFilePath, beanParams, destFilePath);
        } catch (ParsePropertyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
    }

    //生成单个excel
    public  String genExcel(String templateFileName, List<?> list, String resultFileName, HttpServletRequest request, HttpServletResponse response){

        File yTempFile=new File(templateFileName);
        String fileName=yTempFile.getName();

        //doc这种不带点的扩展名
        int index = fileName.lastIndexOf(".");
        String extStr = fileName.substring(index + 1);

        //拼接临时文件夹,得到文件具体路径
        resultFileName=basePath+resultFileName+"."+extStr;

        createExcelByo(templateFileName,list,resultFileName);

        return resultFileName;
    }

    public  void createExcel(String templateFileName, List list, String resultFileName, HttpServletRequest request, HttpServletResponse response){
        FileUtils fileUtil=new FileUtils();
        fileUtil.delFilePath(basePath);

        String filePath="";

        List<List<T>> lists=ListUtils.createList(list, 60000);

        //如果只有一个excel的话,单独输出此文件
        if(lists.size()==1){
            filePath=genExcel( templateFileName,list,  resultFileName,  request,  response);
        }else{
            filePath=genExcelStreng( templateFileName,list,  resultFileName,  request,  response);
        }

        ResponseUtil.responseFile(filePath,  request,  response);

    }

    //生成多个excel,并打成zip
    public  String genExcelStreng(String templateFileName, List list, String resultFileName, HttpServletRequest request, HttpServletResponse response){
        String zipFileName=resultFileName;
        //获取系统临时文件夹
        String basePath=System.getProperty("java.io.tmpdir")+"excelTemple/";

        //读取所有的数据,以60000条为一个list进行拆分
        List<List<T>> lists=ListUtils.createList(list, 60000);

        List<File> srcfile=new ArrayList<File>();

        for (int i = 0; i < lists.size(); i++) {
           String filePath= genExcel( templateFileName, lists.get(i),  resultFileName+"("+(i+1)+")",  request,  response);
           srcfile.add(new File(filePath));
        }

        String responseFileUrl=basePath+zipFileName+".zip";

        File zipfile = new File(responseFileUrl);

        ZipUtil.zipFiles(srcfile, zipfile);

        return  responseFileUrl;

    }

}