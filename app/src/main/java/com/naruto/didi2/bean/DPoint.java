package com.naruto.didi2.bean;

/**
 * xxx class
 *
 * @author yangyong
 * @date 2021/5/8/0008
 */

public class DPoint {
    private double longitude;
    private double latitude;

    public DPoint(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public DPoint() {
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "DPoint{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
