package com.naruto.didi2.bean;

import java.io.Serializable;

/**
 * Created by DELL on 2022/12/23.
 */

public class AppPer extends Cat implements Serializable{

    private static final long serialVersionUID = 2570572736739676342L;
    /**
     * permission : CALL_PHONE
     * mode : DISALLOW
     */

    private String permission;
    private String mode;

    public AppPer(String name) {
        super(name);
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
