package com.naruto.didi2.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 * 自定义dialog class
 *
 * @author yangyong
 * @date 2021/1/27/0027
 */

public class MyDialog extends Dialog {
    private String pkg;

    public MyDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public String getPkg() {
        return pkg;
    }

    public void setPkg(String pkg) {
        this.pkg = pkg;
    }

}
