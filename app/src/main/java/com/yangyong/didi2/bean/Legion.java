package com.yangyong.didi2.bean;

/**
 * xxx class
 *
 * @author yangyong
 * @date 2020/6/30/0030
 */

public class Legion {
    private String name;
    private int level;
    private int power;
    private String team;
    private int htian;

    private Legion() {
    }

    private String printInfo() {
        return toString();
    }

    @Override
    public String toString() {
        return "Legion{" +
                "name='" + name + '\'' +
                ", level=" + level +
                ", power=" + power +
                ", team='" + team + '\'' +
                ", htian=" + htian +
                '}';
    }
}
