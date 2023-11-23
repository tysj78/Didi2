package com.naruto.didi2.activity.test;

import java.util.ArrayList;

/**
 * Created by DELL on 2021/9/9.
 */

public class OneTimeDetails {
    private String pkgName;
    private long useTime;
    private int useCount;
    private long useFlow;
    private ArrayList OneTimeDetailEventList;


    public OneTimeDetails(String pkgName, int useCount) {
        this.pkgName = pkgName;
        this.useCount = useCount;
    }

    public int getUseCount() {
        return useCount;
    }

    public void setUseCount(int useCount) {
        this.useCount = useCount;
    }

    public long getUseFlow() {
        return useFlow;
    }

    public void setUseFlow(long useFlow) {
        this.useFlow = useFlow;
    }

    public OneTimeDetails(String pkgName, long useFlow) {
        this.pkgName = pkgName;
        this.useFlow = useFlow;
    }

    public OneTimeDetails(String pkg, long useTime, ArrayList oneTimeDetailList) {
        this.pkgName = pkg;
        this.useTime = useTime;
        OneTimeDetailEventList = oneTimeDetailList;
    }

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public long getUseTime() {
        return useTime;
    }

    public void setUseTime(long useTime) {
        this.useTime = useTime;
    }

    public ArrayList getOneTimeDetailEventList() {
        return OneTimeDetailEventList;
    }

    public void setOneTimeDetailEventList(ArrayList oneTimeDetailEventList) {
        OneTimeDetailEventList = oneTimeDetailEventList;
    }

    @Override
    public String toString() {
        return "OneTimeDetails{" +
                "pkgName='" + pkgName + '\'' +
                ", useTime=" + useTime +
                ", useCount=" + useCount +
                ", useFlow=" + useFlow +
                ", OneTimeDetailEventList=" +
                '}';
    }

//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    public String getStartTime(){
//        String startTime = null;
//        if(OneTimeDetailEventList.size() > 0){
//            //startTime = DateUtils.formatSameDayTime(OneTimeDetailEventList.get(0).getTimeStamp(), System.currentTimeMillis(), DateFormat.MEDIUM, DateFormat.MEDIUM).toString();
//            startTime = DateTransUtils.stampToDate(OneTimeDetailEventList.get(0).getTimeStamp());
//        }
//        return startTime;
//    }
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    public String getStopTime(){
//        String stopTime = null;
//        if(OneTimeDetailEventList.size() > 0){
//            //stopTime = DateUtils.formatSameDayTime(OneTimeDetailEventList.get(OneTimeDetailEventList.size()-1).getTimeStamp(), System.currentTimeMillis(), DateFormat.MEDIUM, DateFormat.MEDIUM).toString();
//            stopTime = DateTransUtils.stampToDate(OneTimeDetailEventList.get(OneTimeDetailEventList.size()-1).getTimeStamp());
//        }
//        return stopTime;
//    }
}
