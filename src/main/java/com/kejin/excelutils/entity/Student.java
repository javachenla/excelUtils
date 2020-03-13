package com.kejin.excelutils.entity;

import lombok.Data;

/**
 * @Author chenjian
 * @Date 2020/3/11 10:53
 **/
@Data
public class Student {
    private String sex;
    private int id;
    private String name;

    public Student(int id, String name, String sex) {
        this.id = id;
        this.name = name;
        this.sex = sex;
    }


}