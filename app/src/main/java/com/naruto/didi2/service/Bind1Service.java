package com.naruto.didi2.service;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.naruto.didi2.util.LogUtils;

import java.io.IOException;


public class Bind1Service extends Service {
    public MyBinder myBinder = new MyBinder();
    private int count = 0;
    private boolean end = false;
    private MediaPlayer mediaPlayer;
    private ProcessBuilder processBuilder;

    @Override
    public IBinder onBind(Intent intent) {
        LogUtils.e("onBind");
        return myBinder;
    }

    public class MyBinder extends Binder {
        public Bind1Service getServer() {
            return Bind1Service.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.e("onCreate");

//        new Thread(
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        while (!end){
//                            LogUtils.e("count:"+count);
//                            count++;
//                            try {
//                                Thread.sleep(5000);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                }
//        ).start();
        mediaPlayer = new MediaPlayer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.e("onDestroy;");

        end = true;
    }

    /**
     * 提供公共方法，供外部组件调用
     *
     * @return count值
     */
    public int getCount() {
        return count;
    }

    public void setMusic() {
        try {
            AssetFileDescriptor fd = getAssets().openFd("honor.mp3");
            if (!mediaPlayer.isPlaying()) {  //如果是startService导致Activity退出时音乐没有停止，再次进来 bindService Connection上时，会再次调用setMusic
                try {
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
                    mediaPlayer.prepare();
                } catch (Exception e) {
                    Log.d("hint", "can't get to the song");
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 播放
     */
    public void start() {
        LogUtils.e("-----------------音乐开始播放，bindService调用的");
        mediaPlayer.start();
    }

    public void stop() {
        LogUtils.e("-----------------音乐暂停，bindService调用的");
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public void autoClickPo(final int type, final double x, final double y, final double endX, final double endY) {
//        width = act.getWindowManager().getDefaultDisplay().getWidth();
//        height = act.getWindowManager().getDefaultDisplay().getHeight();
        String[] order1 = getCmd(type, x, y, endX, endY);
        String[] order2 = getCmd(type, x, y-50, endX, endY-100);
        String[] order3 = getCmd(type, x, endY-100, endX, endY-150);
        String[] order4 = getCmd(type, x, endY-150, endX, endY-200);
        String[] order5 = getCmd(type, x, endY-200, endX, endY-250);
        String[] order6 = getCmd(type, x, endY-250, endX, endY-300);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                // 线程睡眠0.3s
//                try {
//                    Thread.sleep(300);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                // 利用ProcessBuilder执行shell命令
//                String[] order = getCmd(type, x, y, endX, endY);
//
//                try {
//                    processBuilder = new ProcessBuilder();
//                    processBuilder.command(order);
//                    processBuilder.start();
//                    LogUtils.e("模拟操作执行成功");
//                } catch (Exception e) {
//                    LogUtils.e("模拟操作执行失败：" + e.toString());
//                }
//            }
//        }).start();
        try {
            processBuilder = new ProcessBuilder();
            processBuilder.command(order1);
//            processBuilder.command(order2);
//            processBuilder.command(order3);
//            processBuilder.command(order4);
//            processBuilder.command(order5);
//            processBuilder.command(order6);
            processBuilder.start();
            LogUtils.e("模拟操作执行成功");
        } catch (Exception e) {
            LogUtils.e("模拟操作执行失败：" + e.toString());
        }
    }

    private String[] getCmd(int type, double x, double y, double endX, double endY) {
        switch (type) {
            case 1:
                //点击
                return new String[]{"input", "tap", "" + x, "" + y};
            case 2:
                //滑动 模拟滑动input swipe startX startY endX endY duration(ms)
                return new String[]{"input", "swipe", "" + x, "" + y, "" + endX, "" + endY, "2000"};
        }
        return new String[]{};
    }

    //通过bindService将不会回调此方法
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.e("onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogUtils.e("onUnbind");
        return super.onUnbind(intent);
    }
}
