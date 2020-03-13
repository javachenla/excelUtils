package com.kejin.excelutils.utils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;

/**
 * @Author chenjian
 * @Date 2020/3/11 10:53
 **/
public   class ResponseUtil {

    public static  void  responseFile(String url, HttpServletRequest request, HttpServletResponse response){

        ServletOutputStream out = null;
        FileInputStream ips = null;

        try {
            //获取文件存放的路径
            File file = new File(url);
            String fileName=file.getName();

            if(!file.exists()) {
                //如果文件不存在就跳出
                return;
            }
            ips = new FileInputStream(file);
            response.setContentType("multipart/form-data");
            //防止文件输出时,文件名乱码
            String userAgent = request.getHeader("user-agent");
            if (userAgent != null && userAgent.indexOf("Firefox") >= 0 ||
                    userAgent.indexOf("Chrome") >= 0 ||
                    userAgent.indexOf("Safari") >= 0) {
                fileName= new String((fileName).getBytes(), "ISO8859-1");
            } else {
                fileName= URLEncoder.encode(fileName,"UTF8"); //其他浏览器
            }

            response.addHeader("Content-Disposition", "attachment;filename="+ fileName);
            out = response.getOutputStream();
            //读取文件流
            int len = 0;
            byte[] buffer = new byte[1024 * 10];
            while ((len = ips.read(buffer)) != -1){
                out.write(buffer,0,len);
            }
            out.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                out.close();
                ips.close();
            } catch (Exception e) {
                System.out.println("关闭流出现异常");
                e.printStackTrace();
            }
        }
    }

}
