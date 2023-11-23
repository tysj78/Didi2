package com.naruto.didi2.hook;

/**
 * Created by wzl on 5/16/18.
 */

public interface IHook {
    public void setListener(IHookListener listener);
    public String description();//返回功能描述
}