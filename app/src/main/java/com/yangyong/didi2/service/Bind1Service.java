package com.yangyong.didi2.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.yangyong.didi2.util.LogUtils;

import java.io.IOException;
import java.util.List;


public class Bind1Service extends Service {
    public MyBinder myBinder = new MyBinder();
    private int count = 0;
    private boolean end = false;
    private MediaPlayer mediaPlayer;

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
