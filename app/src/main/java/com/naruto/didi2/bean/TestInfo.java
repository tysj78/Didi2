package com.naruto.didi2.bean;

/**
 * xxx class
 *
 * @author yangyong
 * @date 2020/8/18/0018
 */

public class TestInfo {
    public int code = 0;
    public String timestamp = "";
    public String signature = "";
    public String message = "";
    public String userid = "";
    public String to = "";

    @Override
    public String toString() {
        return "TestInfo{" +
                "code=" + code +
                ", timestamp='" + timestamp + '\'' +
                ", signature='" + signature + '\'' +
                ", message='" + message + '\'' +
                ", userid='" + userid + '\'' +
                ", token='" + to + '\'' +
                '}';
    }
}
