package com.naruto.didi2.bean;

/**
 * Created by DELL on 2021/7/8.
 * WIFI模型
 */

public class MWifiInfo{

    /**
     * <p>
     * 变量说明
     *
     * @author
     * @since 2016-2-26 下午5:17:29
     * @Fields serialVersionUID TODO
     */

    private static final long serialVersionUID = 1L;
    private String wfId;// i


    private String wfName;// wifi名称
    private String wfSSID;// ssid
    private String wfIsHide;// 是否隐藏
    private String wfSecurityType;// 安全类型
    private String wfPassword;// 密码
    private String wfAgent;// 代理
    private String autoJoin;// 自动加入
    private String proxyType;// 代理类型
    private String proxyServer;// 代理服务器地址
    private String proxyServerPort;// 代理端口号
    private String proxyUsername;// 代理用户名
    private String proxypassword;// 代理密码
    private String proxyPacUrl;// 代理服务器配置文件URL
    private String identifier;// 配置唯一标识

    private String wfBssid;//gxb add 20190312

    public String getWfBssid() {
        return wfBssid;
    }

    public void setWfBssid(String wfBssid) {
        this.wfBssid = wfBssid;
    }

    public String getWfId() {
        return wfId;
    }

    public void setWfId(String wfId) {
        this.wfId = wfId;
    }

    public String getWfName() {
        return wfName;
    }

    public void setWfName(String wfName) {
        this.wfName = wfName;
    }

    public String getWfSSID() {
        return wfSSID;
    }

    public void setWfSSID(String wfSSID) {
        this.wfSSID = wfSSID;
    }

    public String isWfIsHide() {
        return wfIsHide;
    }

    public void setWfIsHide(String wfIsHide) {
        this.wfIsHide = wfIsHide;
    }

    public String getWfSecurityType() {
        return wfSecurityType;
    }

    public void setWfSecurityType(String wfSecurityType) {
        this.wfSecurityType = wfSecurityType;
    }

    public String getWfPassword() {
        return wfPassword;
    }

    public void setWfPassword(String wfPassword) {
        this.wfPassword = wfPassword;
    }

    public String getWfAgent() {
        return wfAgent;
    }

    public void setWfAgent(String wfAgent) {
        this.wfAgent = wfAgent;
    }

    public String isAutoJoin() {
        return autoJoin;
    }

    public void setAutoJoin(String autoJoin) {
        this.autoJoin = autoJoin;
    }

    public String getProxyType() {
        return proxyType;
    }

    public void setProxyType(String proxyType) {
        this.proxyType = proxyType;
    }

    public String getProxyServer() {
        return proxyServer;
    }

    public void setProxyServer(String proxyServer) {
        this.proxyServer = proxyServer;
    }

    public String getProxyServerPort() {
        return proxyServerPort;
    }

    public void setProxyServerPort(String proxyServerPort) {
        this.proxyServerPort = proxyServerPort;
    }

    public String getProxyUsername() {
        return proxyUsername;
    }

    public void setProxyUsername(String proxyUsername) {
        this.proxyUsername = proxyUsername;
    }

    public String getProxypassword() {
        return proxypassword;
    }

    public void setProxypassword(String proxypassword) {
        this.proxypassword = proxypassword;
    }

    public String getProxyPacUrl() {
        return proxyPacUrl;
    }

    public void setProxyPacUrl(String proxyPacUrl) {
        this.proxyPacUrl = proxyPacUrl;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
