package com.naruto.didi2.activity;

import android.Manifest;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.MediaController;

import com.naruto.didi2.R;
import com.naruto.didi2.constant.Constants;

import com.naruto.didi2.util.AppUtil;
import com.naruto.didi2.util.PermissionUtils;

import java.io.IOException;

import io.reactivex.functions.Consumer;

public class VideoActivity extends AppCompatActivity implements MediaController.MediaPlayerControl,
        MediaPlayer.OnBufferingUpdateListener,
        SurfaceHolder.Callback{

    private static final String TAG = "yy";
    private MediaPlayer mediaPlayer;
    private MediaController controller;
    private int bufferPercentage = 0;
    private String[] strings = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        initView();
        PermissionUtils.requestPermissions(this, strings, new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {

            }
        });
        mediaPlayer = new MediaPlayer();
        controller = new MediaController(this);
        controller.setAnchorView(findViewById(R.id.ll_root));
        initSurfaceView();
    }

    private void initSurfaceView() {
        SurfaceView videoSuf = (SurfaceView) findViewById(R.id.sv_video);
        videoSuf.setZOrderOnTop(false);
        videoSuf.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        videoSuf.getHolder().addCallback(this);
    }

   /* private void initVideo() {
        //加载指定的视频文件
        String path = Environment.getExternalStorageDirectory().getPath() + "/pic/b8078bb408abd132e99b789f18c344e0.mp4";
        videoView.setVideoPath(path);

        //创建MediaController对象
        MediaController mediaController = new MediaController(this);
        //VideoView与MediaController建立关联
        videoView.setMediaController(mediaController);
        //让VideoView获取焦点
        videoView.requestFocus();
    }*/

    private void requestP() {
        AppUtil.requestPermissions(this, new String[]{}, new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                getVideo();
            }
        });
    }


    private String getVideo() {
        try {
            Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
//                Uri uri = MediaStore.Video.Media.INTERNAL_CONTENT_URI;

            String[] projections = {
                    MediaStore.Video.Media.DISPLAY_NAME,//名称
                    MediaStore.Video.Media.DURATION,//时长
                    MediaStore.Video.Media.SIZE,//大小
                    MediaStore.Video.Media.DATA,//路径
            };
            Cursor cursor = getContentResolver().query(uri, projections, null, null, null);
            if (cursor == null) {
                return "";
            }
            StringBuilder videoInfos = new StringBuilder();
            while (cursor.moveToNext()) {
                StringBuffer videoInfo = new StringBuffer();
                String name = cursor.getString(0);
                String duration = convertTime(cursor.getLong(1));
                float size = convertMb(cursor.getLong(2));//查询出来一bytew
                String address = cursor.getString(3);
                videoInfo.append("文件详情: name:")
                        .append(name)
                        .append("===时长：")
                        .append(duration)
                        .append("===大小：")
                        .append(size)
                        .append("mb===路径：")
                        .append(address);
                videoInfos.append(videoInfo).append("\n");
//                    Log.e(TAG, "文件详情: " + "name:" + name + "===时长：" + duration + "===大小：" + size + "mb===路径：" + address);
            }
            cursor.close();
            Log.e(TAG, "getVideo: " + videoInfos.toString());
            return videoInfos.toString();
        } catch (Exception e) {
            Log.e(TAG, "getVideoException: " + e.getMessage());
        }
        return "";
    }

    public String convertTime(long time) {
        try {
            long hours = time / (1000 * 60 * 60);
            long minutes = (time - hours * (1000 * 60 * 60)) / (1000 * 60);
            long seconds = ((time - hours * (1000 * 60 * 60)) - (minutes * 60 * 1000)) / 1000;
            StringBuffer diffTime = new StringBuffer();
            if (hours == 0) {
                diffTime.append(minutes).append("分").append(seconds).append("秒");
            } else {
                diffTime.append(hours).append("时").append(minutes).append("分").append(seconds).append("秒");
            }
            return diffTime.toString();
        } catch (Exception e) {
            Log.e(Constants.TAG, "Exception: " + e.getMessage());
        }
        return "";
    }

    private float convertSecond(float millisecond) {
        try {
            return millisecond / 1000;
        } catch (Exception e) {
            Log.e(Constants.TAG, "Exception: " + e.getMessage());
        }
        return 0;
    }

    private float convertMb(long bytes) {
        try {
            return (float) Math.round((float) bytes / 1024 / 1024 * 100) / 100;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void initView() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            String path = Environment.getExternalStorageDirectory().getPath() + "/pic/b8078bb408abd132e99b789f18c344e0.mp4";
            mediaPlayer.setDataSource(path);
            mediaPlayer.setOnBufferingUpdateListener(this);
            //mediaPlayer.prepare();

            controller.setMediaPlayer(this);
            controller.setEnabled(true);

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mediaPlayer){
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        controller.show();
        return super.onTouchEvent(event);
    }

    //MediaPlayer
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    //MediaPlayerControl
    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
        bufferPercentage = i;
    }

    @Override
    public void start() {
        if (null != mediaPlayer){
            mediaPlayer.start();
        }
    }

    @Override
    public void pause() {
        if (null != mediaPlayer){
            mediaPlayer.pause();
        }
    }

    @Override
    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    @Override
    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    @Override
    public void seekTo(int i) {
        mediaPlayer.seekTo(i);
    }

    @Override
    public boolean isPlaying() {
        if (mediaPlayer.isPlaying()){
            return true;
        }
        return false;
    }

    @Override
    public int getBufferPercentage() {
        return bufferPercentage;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

    //SurfaceHolder.callback
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mediaPlayer.setDisplay(surfaceHolder);
        mediaPlayer.prepareAsync();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

}
