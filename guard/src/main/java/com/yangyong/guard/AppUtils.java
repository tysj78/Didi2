package com.yangyong.guard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.yangyong.guard.bean.OperationModel;

/**
 * xxx class
 *
 * @author yangyong
 * @date 2020/7/14/0014
 */

public class AppUtils {
    public static void sendMsg(Context context, OperationModel operationModel) {
        Intent intent = new Intent("com.yangyong.access");
        Bundle bundle = new Bundle();
        bundle.putSerializable("operation", operationModel);
        intent.putExtras(bundle);
        context.sendBroadcast(intent);
    }
}
