package com.naruto.didi2.bean;

/**
 * Created by yangyong on 2019/12/17/0017.
 */

public class CheckResult {
    public static final int RESULT_MAYBE_EMULATOR = 0;
    public static final int RESULT_EMULATOR = 1;
    public static final int RESULT_UNKNOWN = 2;
    public static final int RESULT_LIKELY_EMULATOR = 3;
    public int result;
    public String value;

    public CheckResult(int result, String value) {
        this.result = result;
        this.value = value;
    }
}
