package com.naruto.didi2.view;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.naruto.didi2.util.LogUtils;

import java.io.IOException;

/**
 * xxx class
 *
 * @author yangyong
 * @date 2020/9/16/0016
 */

public class LiveCameraView extends SurfaceView implements SurfaceHolder.Callback {
    private Camera mCamera;
    private SurfaceHolder mSurfaceHolder;

    public LiveCameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mSurfaceHolder = this.getHolder();
        mSurfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        LogUtils.e("Start preview display[SURFACE-CREATED]");
        startPreviewDisplay(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (mSurfaceHolder.getSurface() == null) {
            return;
        }
//            Cameras.followScreenOrientation(getContext(), mCamera);
        LogUtils.e("Restart preview display[SURFACE-CHANGED]");
        stopPreviewDisplay();
        startPreviewDisplay(mSurfaceHolder);
    }

    public void setCamera(Camera camera) {
        mCamera = camera;
        Camera.Parameters params = mCamera.getParameters();
        params.setPictureSize(1080,1920);
        params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        params.setFocusMode(Camera.Parameters.FOCUS_MODE_FIXED);
        params.setSceneMode(Camera.Parameters.SCENE_MODE_HDR);
    }

    private void startPreviewDisplay(SurfaceHolder holder) {
        checkCamera();
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.setDisplayOrientation(90);
            mCamera.startPreview();
        } catch (IOException e) {
            LogUtils.e("Error while START preview for camera");
        }
    }

    private void stopPreviewDisplay() {
        checkCamera();
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            LogUtils.e("Error while STOP preview for camera");
        }
    }

    private void checkCamera() {
        if (mCamera == null) {
            throw new IllegalStateException("Camera must be set when start/stop preview, call <setCamera(Camera)> to set");
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        LogUtils.e("Stop preview display[SURFACE-DESTROYED]");
        stopPreviewDisplay();
    }

}
