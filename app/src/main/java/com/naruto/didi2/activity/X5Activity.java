package com.naruto.didi2.activity;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.naruto.didi2.R;
import com.naruto.didi2.util.LogUtils;
import com.naruto.didi2.util.PermissionUtils;
import com.naruto.didi2.view.X5WebView;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsListener;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import io.reactivex.functions.Consumer;

/**
 * 静态集成x5内核
 */

public class X5Activity extends AppCompatActivity implements View.OnClickListener {

    private X5WebView mWebView;
    private EditText mUrlX5;
    private Button mGoX5;
    private ViewGroup mViewParent;
//    private static final String mHomeUrl = "https://192.168.220.232:8012/help";
    private static final String mHomeUrl = "http://soft.imtt.qq.com/browser/tes/feedback.html";
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String m = (String) msg.obj;
            Toast.makeText(X5Activity.this, m, Toast.LENGTH_SHORT).show();
        }
    };
    String[] strings = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_x5);
        initView();

        reqPer();
//        initX5WebView();
//        initX5();
    }

    private void initView() {
        mUrlX5 = (EditText) findViewById(R.id.x5_url);
        mGoX5 = (Button) findViewById(R.id.x5_go);
        mGoX5.setOnClickListener(this);
        mViewParent = (ViewGroup) findViewById(R.id.x5_content);
    }

    private void reqPer() {
        PermissionUtils.requestPermissions(this, strings, new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    initX5();
                } else {
                    Toast.makeText(X5Activity.this, "未开启存储和访问权限", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void initX5() {
        if (QbSdk.canLoadX5(this)) {
            LogUtils.e("x5已准备好");
            initX5WebView();
            return;
        }


        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                LogUtils.e(" onViewInitFinished is " + arg0);
                initX5WebView();
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
                LogUtils.e("内核初始化完成");
            }
        };

        QbSdk.setDownloadWithoutWifi(true);
        //x5内核初始化接口
        QbSdk.setTbsListener(new TbsListener() {
            @Override
            public void onDownloadFinish(int i) {
                LogUtils.e("onDownloadFinish:" + i);
            }

            @Override
            public void onInstallFinish(int i) {
                LogUtils.e("onInstallFinish:" + i);
                if (i == 200)
                    toast("浏览器x5内核安装完成，请重新打开页面");
            }

            @Override
            public void onDownloadProgress(int i) {
//                Toast.makeText(X5Activity.this,"正在下载x5内核",Toast.LENGTH_SHORT).show();
                LogUtils.e("onDownloadProgress:" + i);
            }
        });
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }

    private void toast(String s) {
        Message obtain = Message.obtain();
        obtain.obj = s;
        mHandler.sendMessage(obtain);
    }

    private void initX5WebView() {

        mWebView = new X5WebView(this, null);

        mViewParent.addView(mWebView, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.FILL_PARENT,
                FrameLayout.LayoutParams.FILL_PARENT));


        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.trim().startsWith("tel")) {//特殊情况tel，调用系统的拨号软件拨号【<a href="tel:1111111111">1111111111</a>】
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                } else {
                    String port = url.substring(url.lastIndexOf(":") + 1, url.lastIndexOf("/"));//尝试要拦截的视频通讯url格式(808端口)：【http://xxxx:808/?roomName】
                    if (port.equals("808")) {//特殊情况【若打开的链接是视频通讯地址格式则调用系统浏览器打开】
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    } else {//其它非特殊情况全部放行
                        view.loadUrl(url);
                    }
                }
                return true;
            }

            @Override
            public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
//				super.onReceivedSslError(webView, sslErrorHandler, sslError);
                //同意证书验证
                sslErrorHandler.proceed();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // mTestHandler.sendEmptyMessage(MSG_OPEN_TEST_URL);
//                mTestHandler.sendEmptyMessageDelayed(MSG_OPEN_TEST_URL, 5000);// 5s?
//                if (Integer.parseInt(Build.VERSION.SDK) >= 16)
//                    changGoForwardButton(view);
                /* mWebView.showLog("test Log"); */
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onJsConfirm(WebView arg0, String arg1, String arg2,
                                       JsResult arg3) {
                return super.onJsConfirm(arg0, arg1, arg2, arg3);
            }

            View myVideoView;
            View myNormalView;
            IX5WebChromeClient.CustomViewCallback callback;

            // /////////////////////////////////////////////////////////
            //

            /**
             * 全屏播放配置
             */
            @Override
            public void onShowCustomView(View view, IX5WebChromeClient.CustomViewCallback customViewCallback) {
                FrameLayout normalView = (FrameLayout) findViewById(R.id.web_filechooser);
                ViewGroup viewGroup = (ViewGroup) normalView.getParent();
                viewGroup.removeView(normalView);
                viewGroup.addView(view);
                myVideoView = view;
                myNormalView = normalView;
                callback = customViewCallback;
            }

            @Override
            public void onHideCustomView() {
                if (callback != null) {
                    callback.onCustomViewHidden();
                    callback = null;
                }
                if (myVideoView != null) {
                    ViewGroup viewGroup = (ViewGroup) myVideoView.getParent();
                    viewGroup.removeView(myVideoView);
                    viewGroup.addView(myNormalView);
                }
            }

            @Override
            public boolean onJsAlert(WebView arg0, String arg1, String arg2,
                                     JsResult arg3) {
                /**
                 * 这里写入你自定义的window alert
                 */
                return super.onJsAlert(null, arg1, arg2, arg3);
            }
        });

        mWebView.setDownloadListener(new DownloadListener() {

            @Override
            public void onDownloadStart(String arg0, String arg1, String arg2,
                                        String arg3, long arg4) {
                LogUtils.e("url: " + arg0);
                new AlertDialog.Builder(X5Activity.this)
                        .setTitle("allow to download？")
                        .setPositiveButton("yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        Toast.makeText(
                                                X5Activity.this,
                                                "fake message: i'll download...",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                })
                        .setNegativeButton("no",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        // TODO Auto-generated method stub
                                        Toast.makeText(
                                                X5Activity.this,
                                                "fake message: refuse download...",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                })
                        .setOnCancelListener(
                                new DialogInterface.OnCancelListener() {

                                    @Override
                                    public void onCancel(DialogInterface dialog) {
                                        // TODO Auto-generated method stub
                                        Toast.makeText(
                                                X5Activity.this,
                                                "fake message: refuse download...",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }).show();
            }
        });

        WebSettings webSetting = mWebView.getSettings();
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        // webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        // webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0)
                .getPath());
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // webSetting.setPreFectch(true);
//        long time = System.currentTimeMillis();
        mWebView.loadUrl(mHomeUrl);
//        TbsLog.d("time-cost", "cost time: "
//                + (System.currentTimeMillis() - time));
//        CookieSyncManager.createInstance(this);
//        CookieSyncManager.getInstance().sync();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.x5_go:
                // TODO 21/11/11
                String url = mUrlX5.getText().toString();
                mWebView.loadUrl(url);
                mWebView.requestFocus();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null)
            mWebView.destroy();
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView != null && mWebView.canGoBack()) {
                mWebView.goBack();
                return true;
            } else
                return super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }
}
