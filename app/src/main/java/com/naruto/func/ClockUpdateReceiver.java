package com.naruto.func;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.naruto.didi2.R;
import com.naruto.didi2.util.LogUtils;

import java.util.Arrays;
import java.util.Calendar;


public class ClockUpdateReceiver extends AppWidgetProvider {

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
//        LogUtils.e("getComponent:"+intent.getComponent());
        LogUtils.e("ClockUpdateReceiver onReceive:" + intent.getAction());
//        LogUtils.e("getData:"+intent.getData());
//        LogUtils.e("getCategories:"+intent.getCategories());
//        LogUtils.e("getType:"+intent.getType());
//        LogUtils.e("getScheme:"+intent.getScheme());
//        AppUtil.getInstance().callBack("updateClock");
        String action = intent.getAction();
        if (action == null) {
            return;
        }
        switch (action) {
            case "naruto.intent.clockupdate":
                updateWidget(context);
                break;
        }

    }

    private void updateWidget(Context context) {
        try {
            Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int min = c.get(Calendar.MINUTE);

            String m = "";
            if (min < 10) {
                m = "0" + min;
            } else {
                m = String.valueOf(min);
            }
            String h = String.valueOf(hour);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.activity_clock);
            views.setTextViewText(R.id.bt_hour, h);
            views.setTextViewText(R.id.bt_minute, m);
            AppWidgetManager instance = AppWidgetManager.getInstance(context);
            instance.updateAppWidget(new ComponentName(context, ClockUpdateReceiver.class), views);
        } catch (Exception e) {
            LogUtils.exception(e);
        }
    }

    private void updateWidget(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        try {
            Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int min = c.get(Calendar.MINUTE);

            String m = "";
            if (min < 10) {
                m = "0" + min;
            } else {
                m = String.valueOf(min);
            }
            String h = String.valueOf(hour);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.activity_clock);
            views.setTextViewText(R.id.bt_hour, h);
            views.setTextViewText(R.id.bt_minute, m);
            appWidgetManager.updateAppWidget(appWidgetIds, views);
        } catch (Exception e) {
            LogUtils.exception(e);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        LogUtils.e("ClockUpdateReceiver onUpdate");
        LogUtils.e("appWidgetIds:" + Arrays.toString(appWidgetIds));
        updateWidget(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        LogUtils.e("ClockUpdateReceiver onEnabled");
    }
}
