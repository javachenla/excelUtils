package com.kejin.excelutils.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author chenjian
 * @Date 2020/3/11 10:53
 **/
public class ListUtils {
    /**
     * 将list拆分成多给指定的大小的list
     */
    public static <T> List<List<T>>  createList(List<T> target, int size) {
        List<List<T>> listArr = new ArrayList<List<T>>();
        //获取被拆分的数组个数
        int arrSize = target.size()%size==0?target.size()/size:target.size()/size+1;
        for(int i=0;i<arrSize;i++) {
            List<T>    sub = new ArrayList<T>();
            //把指定索引数据放入到list中
            for(int j=i*size;j<=size*(i+1)-1;j++) {
                if(j<=target.size()-1) {
                    sub.add(target.get(j));
                }
            }
            listArr.add(sub);
        }
        return listArr;
    }

}