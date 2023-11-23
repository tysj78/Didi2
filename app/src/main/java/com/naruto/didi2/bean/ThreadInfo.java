package com.naruto.didi2.bean;

/**
 * xxx class
 *
 * @author yangyong
 * @date 2020/7/2/0002
 */

public class ThreadInfo {
    private int t_id;
    private String url;
    private int start;
    private long end;
    private long finished;

    public ThreadInfo() {
    }

    public ThreadInfo(String url, int start, long end, long finished) {
        this.url = url;
        this.start = start;
        this.end = end;
        this.finished = finished;
    }

    public ThreadInfo(int t_id, String url, int start, long end, long finished) {
        this.t_id = t_id;
        this.url = url;
        this.start = start;
        this.end = end;
        this.finished = finished;
    }

    public int getT_id() {
        return t_id;
    }

    public void setT_id(int t_id) {
        this.t_id = t_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public long getFinished() {
        return finished;
    }

    public void setFinished(long finished) {
        this.finished = finished;
    }

    @Override
    public String toString() {
        return "ThreadInfo{" +
                "t_id=" + t_id +
                ", url='" + url + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", finished=" + finished +
                '}';
    }
}
