package com.naruto.didi2.bean;

/**
 * Created by famgy on 6/11/18.
 */

public class SmsHistory {
    public int sms_id;
    public String sms_type;
    public String sms_address;
    public String sms_subject;
    public String sms_date;

    @Override
    public String toString() {
        return "SmsHistory{" +
                "sms_id=" + sms_id +
                ", sms_type='" + sms_type + '\'' +
                ", sms_address='" + sms_address + '\'' +
                ", sms_subject='" + sms_subject + '\'' +
                ", sms_date='" + sms_date + '\'' +
                '}';
    }
}
