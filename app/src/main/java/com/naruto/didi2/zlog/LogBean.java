package com.naruto.didi2.zlog;

/**
 */

public class LogBean {

    private String logText;
    private LogType logType;

    public LogBean(String logText, LogType logType) {
        this.logText = logText;
        this.logType = logType;
    }

    @Override
    public String toString() {
        return "LogBean{" +
                "logText='" + logText + '\'' +
                ", logType=" + logType +
                '}';
    }

    public String getLogText() {
        return logText;
    }

    public void setLogText(String logText) {
        this.logText = logText;
    }

    public LogType getLogType() {
        return logType;
    }

    public void setLogType(LogType logType) {
        this.logType = logType;
    }
}
