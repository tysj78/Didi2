package com.naruto.didi2.database;

import java.util.List;

/**
 * Student表接口 class
 *
 * @author yangyong
 * @date 2021/4/1/0001
 */

public interface StudentDao {
    void add(Student student);
    void add(List<Student> student);
    void deleteAll();
    void updateByName(String name,Student student);
    Student queryByName(String name);
    List<Student> query();

}
