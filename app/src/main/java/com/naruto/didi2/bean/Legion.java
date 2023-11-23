package com.naruto.didi2.bean;

import java.io.Serializable;

/**
 * xxx class
 *
 * @author yangyong
 * @date 2020/6/30/0030
 */

public class Legion implements Serializable{

    private static final long serialVersionUID = -8960560598037353461L;
    /**
     * name : 鸣人
     * level : 200
     * power : 600000
     * team : 第七班
     * htian : 12
     */

    private String name;
    private int level;
    private int power;
    private String team;
    private int htian;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public int getHtian() {
        return htian;
    }

    public void setHtian(int htian) {
        this.htian = htian;
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
