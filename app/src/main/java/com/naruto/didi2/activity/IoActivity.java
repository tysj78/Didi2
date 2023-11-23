package com.naruto.didi2.activity;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;

import com.naruto.didi2.R;
import com.naruto.didi2.constant.Constants;


import java.io.File;

public class IoActivity extends AppCompatActivity implements View.OnClickListener {
    //    String url = "https://7acf6045ce49a8441b8739d33b07a922.dd.cdntips.com/download.sj.qq.com/upload/connAssitantDownload/upload/MobileAssistant_1.apk?mkey=5d6e2dbcca631533&f=9870&cip=202.99.51.198&proto=https";
    String url = "http://download.voicecloud.cn/100LYB/recordbox-v3.0.1350.apk";
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, Manifest.permission.REQUEST_INSTALL_PACKAGES};
    private Button main_load;
    private long mTaskId;
    private DownloadManager downloadManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_io);
        initView();
        requestP();
    }

    public void downLoad(String downloadUrl) {
        //创建下载任务,downloadUrl就是下载链接
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadUrl));
//指定下载路径和下载文件名
        request.setDestinationInExternalPublicDir("/downloadfile/", "33");
//获取下载管理器
        DownloadManager downloadManager = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);
//将下载任务加入下载队列，否则不会进行下载
        downloadManager.enqueue(request);
    }

    private void requestP() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 检查该权限是否已经获取
            int i = ContextCompat.checkSelfPermission(this, permissions[0]);
            int u = ContextCompat.checkSelfPermission(this, permissions[1]);
            int y = ContextCompat.checkSelfPermission(this, permissions[2]);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (i != PackageManager.PERMISSION_GRANTED || u != PackageManager.PERMISSION_GRANTED || y != PackageManager.PERMISSION_GRANTED) {
                // 如果没有授予该权限，就去提示用户请求
                ActivityCompat.requestPermissions(this, permissions, 321);
            }

//            // 检查该权限是否已经获取
//            int u = ContextCompat.checkSelfPermission(this, permissions[1]);
//            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
//            if (u != PackageManager.PERMISSION_GRANTED) {
//                // 如果没有授予该权限，就去提示用户请求
//                ActivityCompat.requestPermissions(this, permissions, 322);
//            }

//            // 检查该权限是否已经获取
//            int y = ContextCompat.checkSelfPermission(this, permissions[2]);
//            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
//            if (y != PackageManager.PERMISSION_GRANTED) {
//                // 如果没有授予该权限，就去提示用户请求
//                ActivityCompat.requestPermissions(this, permissions, 323);
//            }
        }
    }

    private void initView() {
        main_load = (Button) findViewById(R.id.main_load);

        main_load.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_load:
//                downLoad(url);
                downloadAPK(url, "yyb.apk");
                break;
        }
    }

    //使用系统下载器下载
    private void downloadAPK(String versionUrl, String versionName) {
        //创建下载任务
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(versionUrl));
//        request.setAllowedOverRoaming(false);//漫游网络是否可以下载

        //设置文件类型，可以在下载结束后自动打开该文件
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        String mimeString = mimeTypeMap.getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(versionUrl));
        request.setMimeType(mimeString);

        //在通知栏中显示，默认就是显示的
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setVisibleInDownloadsUi(true);
        request.setTitle("录音宝下载");
        request.setDescription("新版录音宝下载中...");
        //sdcard的目录下的download文件夹，必须设置
        request.setDestinationInExternalPublicDir("/downloadfile/", versionName);
        //request.setDestinationInExternalFilesDir(),也可以自己制定下载路径

        //将下载请求加入下载队列
        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        //加入下载队列后会给该任务返回一个long型的id，
        //通过该id可以取消任务，重启任务等等，看上面源码中框起来的方法
        mTaskId = downloadManager.enqueue(request);

        //注册广播接收者，监听下载状态
        registerReceiver(receiver,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    //广播接受者，接收下载状态
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            checkDownloadStatus();//检查下载状态
        }
    };

    //检查下载状态
    private void checkDownloadStatus() {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(mTaskId);//筛选下载任务，传入任务ID，可变参数
        Cursor c = downloadManager.query(query);
        if (c.moveToFirst()) {
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status) {
                case DownloadManager.STATUS_PAUSED:
                    Log.e(Constants.TAG, ">>>下载暂停");
                case DownloadManager.STATUS_PENDING:
                    Log.e(Constants.TAG, ">>>下载延迟");
                case DownloadManager.STATUS_RUNNING:
                    Log.e(Constants.TAG, ">>>正在下载");
                    break;
                case DownloadManager.STATUS_SUCCESSFUL:
                    Log.e(Constants.TAG, ">>>下载完成");
                    //下载完成安装APK
                    String downloadPath = Environment.getExternalStoragePublicDirectory("/downloadfile/").getAbsolutePath() + File.separator + "yyb.apk";
                    installAPK(new File(downloadPath));
                    break;
                case DownloadManager.STATUS_FAILED:
                    Log.e(Constants.TAG, ">>>下载失败");
                    break;
            }
        }
        if (c != null) {
            c.close();
        }
    }

    //下载到本地后执行安装
    protected void installAPK(File file) {
        try {
            if (!file.exists()) return;
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = null;
//        Uri uri = Uri.parse("file://" + file.toString());//7.0以上已废弃
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                uri = FileProvider.getUriForFile(this, getPackageName() + ".provider", file);
            } else {
                uri = Uri.parse("file://" + file.toString());
            }

            Log.e(Constants.TAG, "filepath: " + file.getAbsolutePath());
            Log.e(Constants.TAG, "uri: " + uri);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            //在服务中开启activity必须设置flag,后面解释
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);
        } catch (Exception e) {
            Log.e(Constants.TAG, "安装异常: " + e.getMessage());
        }

    }
}
