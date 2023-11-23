package com.naruto.didi2.bean;

import java.io.Serializable;
import java.util.List;

/**
 * xxx class
 *
 * @author yangyong
 * @date 2020/7/10/0010
 */

public class OperationModel implements Serializable {
    private static final long serialVersionUID = 1L;
    private int durationTime;
    private int delayTime;
    private List<LocationModel> list;

    public int getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(int durationTime) {
        this.durationTime = durationTime;
    }

    public int getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(int delayTime) {
        this.delayTime = delayTime;
    }

    public List getList() {
        return list;
    }

    public void setList(List<LocationModel> list) {
        this.list = list;
    }
}
