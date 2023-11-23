/**
 * Copyright (C) 2016, 上海上讯信息技术有限公司. All rights reserved.
 *
 * @version V3.0
 */

package com.naruto.didi2.bean;


public class MScreenPasswordInfo{

    /**
     * <p>
     * 变量说明
     *
     * @author
     * @since 2016-2-29 下午4:45:38
     * @Fields serialVersionUID TODO
     */

    private static final long serialVersionUID = 1L;
    private String spiID;// id
    //private String spiMaxLength;// 最大长度
    private String spiMinLength;// 最小长度
    private String spiLeastCharNum;// 至少英文字符数
    private String spiLeastUpCharNum;// 至少大写字符数
    private String spiLeastLowCharNum;// 至少小写字符数
    //private String spiMostNotCharNum;// 至多非英文字符数
    private String spiLeastArabicNum;// 至少阿拉伯数字数
    private String spiLeastSymbolNum;// 至少特殊字符数
    private String spiExpiredTime;// 密码过期时间
    private String spiPWLimit;// 密码历史限制
    private int spiPWMostTryTimes;// 密码登录最大尝试数
    private String spiMaxFailedAttemptsEraseDevice;
    private String identifier;//配置唯一标识符
    private String pinPass = "";//pin码

    @Override
    public String toString() {
        return "MScreenPasswordInfo{" +
                "spiID='" + spiID + '\'' +
                ", spiMinLength='" + spiMinLength + '\'' +
                ", spiLeastCharNum='" + spiLeastCharNum + '\'' +
                ", spiLeastUpCharNum='" + spiLeastUpCharNum + '\'' +
                ", spiLeastLowCharNum='" + spiLeastLowCharNum + '\'' +
                ", spiLeastArabicNum='" + spiLeastArabicNum + '\'' +
                ", spiLeastSymbolNum='" + spiLeastSymbolNum + '\'' +
                ", spiExpiredTime='" + spiExpiredTime + '\'' +
                ", spiPWLimit='" + spiPWLimit + '\'' +
                ", spiPWMostTryTimes=" + spiPWMostTryTimes +
                ", spiMaxFailedAttemptsEraseDevice='" + spiMaxFailedAttemptsEraseDevice + '\'' +
                ", identifier='" + identifier + '\'' +
                ", pinPass='" + pinPass + '\'' +
                ", passwordComplexity='" + passwordComplexity + '\'' +
                '}';
    }

    private String passwordComplexity ;//密码复杂度 0：字母+数字  1：字母+特殊符号  2：所有

    public String getPasswordComplexity() {
        return passwordComplexity;
    }

    public void setPasswordComplexity(String passwordComplexity) {
        this.passwordComplexity = passwordComplexity;
    }

    public String getSpiMaxFailedAttemptsEraseDevice() {
        return spiMaxFailedAttemptsEraseDevice;
    }

    public void setSpiMaxFailedAttemptsEraseDevice(
            String spiMaxFailedAttemptsEraseDevice) {
        this.spiMaxFailedAttemptsEraseDevice = spiMaxFailedAttemptsEraseDevice;
    }

    public String getSpID() {
        return spiID;
    }

    public void setSpiID(String spID) {
        this.spiID = spID;
    }

    /*
        public String getSpMaxLength() {
            return spiMaxLength;
        }

        public void setSpiMaxLength(String spMaxLength) {
            this.spiMaxLength = spMaxLength;
        }
    */
    public String getSpiMinLength() {
        return spiMinLength;
    }

    public void setSpiMinLength(String spiMinLength) {
        this.spiMinLength = spiMinLength;
    }

    public String getSpiLeastCharNum() {
        return spiLeastCharNum;
    }

    public void setSpiLeastCharNum(String spiLeastCharNum) {
        this.spiLeastCharNum = spiLeastCharNum;
    }

    public String getSpiLeastUpCharNum() {
        return spiLeastUpCharNum;
    }

    public void setSpiLeastUpCharNum(String spiLeastUpCharNum) {
        this.spiLeastUpCharNum = spiLeastUpCharNum;
    }

    public String getSpiLeastLowCharNum() {
        return spiLeastLowCharNum;
    }

    public void setSpiLeastLowCharNum(String spiLeastLowCharNum) {
        this.spiLeastLowCharNum = spiLeastLowCharNum;
    }

    /*
        public String getSpiMostNotCharNum() {
            return spiMostNotCharNum;
        }

        public void setSpiMostNotCharNum(String spiMostNotCharNum) {
            this.spiMostNotCharNum = spiMostNotCharNum;
        }
    */
    public String getSpiLeastArabicNum() {
        return spiLeastArabicNum;
    }

    public void setSpiLeastArabicNum(String spiLeastArabicNum) {
        this.spiLeastArabicNum = spiLeastArabicNum;
    }

    public String getSpiLeastSymbolNum() {
        return spiLeastSymbolNum;
    }

    public void setSpiLeastSymbolNum(String spiLeastSymbolNum) {
        this.spiLeastSymbolNum = spiLeastSymbolNum;
    }

    public String getSpiExpiredTime() {
        return spiExpiredTime;
    }

    public void setSpiExpiredTime(String spiExpiredTime) {
        this.spiExpiredTime = spiExpiredTime;
    }

    public String getSpiPWLimit() {
        return spiPWLimit;
    }

    public void setSpiPWLimit(String spiPWLimit) {
        this.spiPWLimit = spiPWLimit;
    }

    public int getSpiPWMostTryTimes() {
        return spiPWMostTryTimes;
    }

    public void setSpiPWMostTryTimes(int spiPWMostTryTimes) {
        this.spiPWMostTryTimes = spiPWMostTryTimes;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getSpiID() {
        return spiID;
    }

    public String getPinPass() {
        return pinPass;
    }

    public void setPinPass(String pinPass) {
        this.pinPass = pinPass;
    }
}
