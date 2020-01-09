package com.yangyong.didi2.activity;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.yangyong.didi2.R;
import com.yangyong.didi2.util.AppUtil;

import io.reactivex.functions.Consumer;

public class VideoActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "yy";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

        }
    };
    private Button query_video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        initView();
    }

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
            Log.e(TAG, "getVideo: "+ videoInfos.toString());
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
            Log.e("yy", "Exception: " + e.getMessage());
        }
        return "";
    }

    private float convertSecond(float millisecond) {
        try {
            return millisecond / 1000;
        } catch (Exception e) {
            Log.e("yy", "Exception: " + e.getMessage());
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
        query_video = (Button) findViewById(R.id.query_video);

        query_video.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.query_video:
//                requestP();
                getVideo();
                break;
        }
    }
}
