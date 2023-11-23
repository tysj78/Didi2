package com.mobilewise.didi2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yangyong on 2020/2/27/0027.
 */

public class Const<T> {
    public static String name = "5";
    public String age = "";
    public String sex = "";

    public T mT;

    public Const(T t) {
        this.mT = t;
    }

    public Const(String age, String sex) {
        this.age = age;
        this.sex = sex;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Const.name = name;
    }

    public Const() {
    }

    @Override
    public String toString() {
        return "Const{" +
                "age='" + age + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }

    static {
        name = "66";
    }


    /***
     * 是否包含指定字符串,不区分大小写
     * @param input : 原字符串
     * @param regex
     * @return
     */
    public static boolean contain2(String input, String regex) {
        if (input == null || input.equals("")) {
            return false;
        }
        if (regex == null || regex.equals("")) {
            return false;
        }
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(input);
        return m.find();
    }
}
