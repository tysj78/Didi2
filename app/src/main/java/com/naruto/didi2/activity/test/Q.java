package com.naruto.didi2.activity.test;

/**
 * xxx class
 *
 * @author yangyong
 * @date 2021/6/9/0009
 */

public class Q {
    private String name;
    private int age;
    public String gender;


    public Q() {
    }

    public Q(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    private static String fly(){
        return "这波起飞！！";
    }

    @Override
    public String toString() {
        return "Q{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
