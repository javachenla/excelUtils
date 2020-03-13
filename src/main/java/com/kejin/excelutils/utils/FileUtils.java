package com.kejin.excelutils.utils;

import java.io.File;

/**
 * @Author chenjian
 * @Date 2020/3/11 15:21
 **/
public class FileUtils {

    public boolean delFilePath(String filePath) {
        boolean flag = true;
        if(filePath != null) {
            File file = new File(filePath);
            if(file.exists()) {
                File[] filePaths = file.listFiles();
                for(File f : filePaths) {
                    if(f.isFile()) {
                        f.delete();
                    }
                    if(f.isDirectory()){
                        String fpath = f.getPath();
                        delFilePath(fpath);
                        f.delete();
                    }
                }
            }
        }else {
            flag = false;
        }
        return flag;

    }

}
