//package com.naruto.didi2.activity.third;
//
//import android.content.Intent;
//import android.media.AudioManager;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.view.SurfaceHolder;
//import android.view.SurfaceView;
//
//
//import com.naruto.didi2.R;
//import com.naruto.didi2.service.SendDataService;
//import com.naruto.didi2.util.AppUtil;
//import com.naruto.didi2.util.LogUtils;
//
//import java.io.IOException;
//
//import tv.danmaku.ijk.media.player.IjkMediaPlayer;
//
//public class IjkPlayerActivity extends AppCompatActivity {
//
//    private SurfaceView sv_video;
//    private IjkMediaPlayer mPlayer;
//    private String url = "http://shenying.5gzvip.idcfengye.com/shenying/upload/b8078bb408abd132e99b789f18c344e0.mp4";
////    private String url = "http://192.168.155.1:8080/shenying/upload/b8078bb408abd132e99b789f18c344e0.mp4";
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_ijk_player);
//        initView();
//
//        AppUtil.getInstance().setTransparentForWindow(this);
//        Intent intent = new Intent();
//        intent.setClass(this, SendDataService.class);
//        startService(intent);
//        sv_video.getHolder().addCallback(callback);
//    }
//
//    private void initView() {
//        sv_video = (SurfaceView) findViewById(R.id.sv_video);
//    }
//
//    @Override
//    protected void onDestroy() {
//        sv_video.getHolder().removeCallback(callback);
//        super.onDestroy();
//    }
//
//    private SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {
//        @Override
//        public void surfaceCreated(SurfaceHolder holder) {
//            LogUtils.e("surfaceCreated");
//            createPlayer();
//            mPlayer.setDisplay(sv_video.getHolder());
//        }
//
//        @Override
//        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//            LogUtils.e("surfaceChanged");
//        }
//
//        @Override
//        public void surfaceDestroyed(SurfaceHolder holder) {
//            LogUtils.e("surfaceDestroyed");
//            releasePlayer();
//        }
//    };
//
//    private void createPlayer() {
//        if (mPlayer == null) {
//            mPlayer = new IjkMediaPlayer();
//            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//            try {
//
////                mPlayer.setDataSource("http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f30.mp4");
//                mPlayer.setDataSource(url);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            mPlayer.prepareAsync();
//        }
//    }
//
//    private void releasePlayer() {
//        if (mPlayer != null) {
//            mPlayer.stop();
//            mPlayer.release();
//            mPlayer = null;
//        }
//        IjkMediaPlayer.native_profileEnd();
//    }
//}
