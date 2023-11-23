package com.naruto.didi2.bean;

import java.io.Serializable;

/**
 * xxx class
 *
 * @author yangyong
 * @date 2020/7/10/0010
 */

public class LocationModel implements Serializable {
    private static final long serialVersionUID = 1L;
    private int x;
    private int y;

    public LocationModel() {
    }

    public LocationModel(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
