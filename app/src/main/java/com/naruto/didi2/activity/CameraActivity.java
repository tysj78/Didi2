package com.naruto.didi2.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.naruto.didi2.R;
import com.naruto.didi2.view.LiveCameraView;

import com.naruto.didi2.util.FileUtils;
import com.naruto.didi2.util.LogUtils;
import com.naruto.didi2.util.PermissionUtils;

import io.reactivex.functions.Consumer;


public class CameraActivity extends AppCompatActivity implements View.OnClickListener {

    private final int REQ_CODE = 300;
    private LiveCameraView sv_image;
    private Button bt_start;
    private Camera camera;
    private BitmapCallback bitmapCallback;
    private ImageView iv_capture;
    private TextView tv_save_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        initView();
        bitmapCallback = new BitmapCallback();
        initCamera();
        requestPer();
    }

    private void requestPer() {
        String[] per = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        PermissionUtils.requestPermissions(this, per, new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
            }
        });
    }

    private void initCamera() {
        camera = getCamera(0);
        sv_image.setCamera(camera);
//        WindowManager windowManager = getWindowManager();
//        Display display = windowManager.getDefaultDisplay();
//        int screenWidth = display.getWidth();
//        int screenHeight = display.getHeight();
//        LogUtils.e(screenWidth+" "+screenHeight);
    }

    private Camera getCamera(int cameraId) {
//        return Camera.open();
        return Camera.open(cameraId);
    }

    private void initView() {
        sv_image = (LiveCameraView) findViewById(R.id.sv_image);
        bt_start = (Button) findViewById(R.id.bt_start);
        bt_start.setOnClickListener(this);
        iv_capture = (ImageView) findViewById(R.id.iv_capture);
        iv_capture.setOnClickListener(this);
        tv_save_status = (TextView) findViewById(R.id.tv_save_status);
        tv_save_status.setOnClickListener(this);
    }

    private void start(Camera camera) {
        try {
//            camera.setPreviewDisplay(holder);
//            camera.setDisplayOrientation(90);
            camera.startPreview();
            camera.takePicture(null, null, bitmapCallback);
            //拍照完需要回收资源

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void openCamera() {
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//用来打开相机的Intent
        if (takePhotoIntent.resolveActivity(getPackageManager()) != null) {//这句作用是如果没有相机则该应用不会闪退，要是不加这句则当系统没有相机应用的时候该应用会闪退
            startActivityForResult(takePhotoIntent, REQ_CODE);//启动相机
        }
    }

    private class BitmapCallback implements Camera.PictureCallback {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            onPictureTaken(BitmapFactory.decodeByteArray(data, 0, data.length));
        }

        void onPictureTaken(Bitmap bitmap) {
//            iv_capture.setImageBitmap(bitmap);
            tv_save_status.setText("save file");
            new MyAsyncTask().execute(bitmap);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_start:
                start(camera);
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtils.e("onRestart");
        initCamera();
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtils.e("onStop");
        if (camera != null) {
            camera.release();
        }
    }

    private class MyAsyncTask extends AsyncTask<Bitmap, Void, Integer> {

        @Override
        protected Integer doInBackground(Bitmap... Bitmap) {
            try {
                FileUtils.saveBitmap(Bitmap[0]);
            } catch (Exception e) {
                e.printStackTrace();
                return 1;
            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            if (integer == 0) {
                tv_save_status.setText("文件保存完成");
            } else {
                tv_save_status.setText("文件保存失败");
            }
//            initCamera();
            if (camera != null) {
                camera.startPreview();
            }
        }
    }
}
