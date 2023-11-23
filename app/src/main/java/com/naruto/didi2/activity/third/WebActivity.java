package com.naruto.didi2.activity.third;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.naruto.didi2.R;

public class WebActivity extends AppCompatActivity implements View.OnClickListener {

    private WebView mContentWv;
    private Button mCalljsBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initView();

        initWebView();
    }

    private void initWebView() {
     /*   //动态创建一个WebView对象并添加到LinearLayout中
        webView = new WebView(getApplication());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        webView.setLayoutParams(params);
        ll_root.addView(webView);*/
        //不跳转到其他浏览器
        mContentWv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        WebSettings settings = mContentWv.getSettings();
        //支持JS
        settings.setJavaScriptEnabled(true);
        //加载本地html文件
        mContentWv.loadUrl("file:///android_asset/JavaAndJavaScriptCall.html");
        mContentWv.addJavascriptInterface(new JSInterface(), "Android");
    }

    private void initView() {
        mContentWv = (WebView) findViewById(R.id.wv_content);
        mCalljsBt = (Button) findViewById(R.id.bt_calljs);
        mCalljsBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_calljs:
                // TODO 21/10/29
                String aa = "你好";
                mContentWv.loadUrl("javascript:javaCallJs(" + "'" + aa + "'" + ")");
                break;
            default:
                break;
        }
    }

    private class JSInterface {
        //JS需要调用的方法
        @JavascriptInterface
        public void showToast(String arg) {
            Toast.makeText(WebActivity.this, arg, Toast.LENGTH_SHORT).show();
        }
    }
}
