package com.naruto.didi2.bean;

import java.util.List;

/**
 * xxx class
 *
 * @author yangyong
 * @date 2021/5/27/0027
 */

public class Behavior {

    /**
     * index : 1
     * name : yang
     * MD5 : de1713cfa6727f48f86714014bdf943b
     * actionCode : 010701
     * decide : ["<2020-12-14 12:37:04> 检测到攻击","<2020-12-14 12:37:04> 判定为注入攻击"]
     */

    private String index;
    private String name;
    private String MD5;
    private String actionCode;
    private List<String> decide;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMD5() {
        return MD5;
    }

    public void setMD5(String MD5) {
        this.MD5 = MD5;
    }

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public List<String> getDecide() {
        return decide;
    }

    public void setDecide(List<String> decide) {
        this.decide = decide;
    }

    @Override
    public String toString() {
        return "Behavior{" +
                "index='" + index + '\'' +
                ", name='" + name + '\'' +
                ", MD5='" + MD5 + '\'' +
                ", actionCode='" + actionCode + '\'' +
                ", decide=" + decide +
                '}';
    }
}
