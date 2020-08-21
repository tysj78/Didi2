package com.yangyong.didi2.bean;

import com.yangyong.didi2.MyApp;
import com.yangyong.didi2.util.AppUtil;

/**
 * Created by yangyong on 2019/12/20/0020.
 */

public class MnInfo {
    public boolean isM;
    public String infos = "";

    public MnInfo(boolean isM, String infos) {
        this.isM = isM;
        this.infos = infos;
    }

    public MnInfo() {
    }
}
