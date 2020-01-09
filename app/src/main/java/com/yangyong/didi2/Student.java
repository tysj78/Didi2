package com.yangyong.didi2;

import android.support.annotation.NonNull;

/**
 * Created by yangyong on 2019/11/11/0011.
 */

public class Student implements Comparable<Student> {
    private int age;

    public Student(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    @Override
    public int compareTo(Student student) {  // 重写compareTo方法

        return (this.age < student.age) ? -1 : ((this.age == student.age) ? 0 : 1);
    }
}
