package com.naruto.didi2.activity;

import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


import com.naruto.didi2.R;
import com.naruto.didi2.util.LogUtils;

import java.io.File;

public class RecordActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bt_start_record;
    private MediaRecorder mediaRecorder;
    private String filePath;
    private Button bt_stop_record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        initView();

    }

    private void initView() {
        bt_start_record = (Button) findViewById(R.id.bt_start_record);

        bt_start_record.setOnClickListener(this);
        bt_stop_record = (Button) findViewById(R.id.bt_stop_record);
        bt_stop_record.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_start_record:
                startRec();
                break;
            case R.id.bt_stop_record:
                stopRec();
                break;
        }
    }

    private void stopRec() {
        if (mediaRecorder != null) {
            LogUtils.e("停止捕获");
            mediaRecorder.stop();
            LogUtils.e("释放资源");
            mediaRecorder.release();
            mediaRecorder = null;
            LogUtils.e("录制完毕==");
        }
    }

    private void startRec() {
        try {
            LogUtils.e("开始录音");
            LogUtils.e("实例化一个录音机");
            mediaRecorder = new MediaRecorder();
            LogUtils.e("指定录音机的声音源");
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            LogUtils.e("设置录制的文件输出的格式");
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
            //4.制定录音文件的名称
            LogUtils.e("制定录音文件的名称");
            File file = new File(Environment.getExternalStorageDirectory() + "/records", System.currentTimeMillis() + ".wav");
            filePath = file.getAbsolutePath();
            LogUtils.e("制定录音文件的名称filePath=" + filePath);
            mediaRecorder.setOutputFile(filePath);
            LogUtils.e("设置音频的编码");
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
            LogUtils.e("开始准备录音");
            mediaRecorder.prepare();
            LogUtils.e("开始录音");
            mediaRecorder.start();
        } catch (Exception e) {
            LogUtils.e(e.toString());
            e.printStackTrace();
        }
    }

}
