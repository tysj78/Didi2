package com.naruto.didi2.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.naruto.didi2.R;
import com.naruto.didi2.util.LogUtils;
import com.naruto.didi2.util.OkHttpUtil;

import java.util.HashMap;

public class RequestActivity extends AppCompatActivity implements View.OnClickListener {
    //    private String url = "https://api.tianapi.com/txapi/poetry?key=e3d610dde0076bbc53d1421b12cfee35";
//    private String url = "http://api.tianapi.com/esports/index?key=e3d610dde0076bbc53d1421b12cfee35&num=3";
    private String url = "http://api.tianapi.com/esports/index";
    private String TAG = "yy";
    private Button main_request;
    private TextView main_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        initView();
    }

    private void initView() {
        main_request = (Button) findViewById(R.id.main_request);
        main_txt = (TextView) findViewById(R.id.main_txt);

        main_request.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_request:
//                OkHttpUtil.getInstance().doGet(url, new OkHttpUtil.DataCallBack() {
//                    @Override
//                    public void onSuccess(String s) {
//                        main_txt.setText(s);
//                    }
//
//                    @Override
//                    public void onFailure(String f) {
//                        LogUtils.e("请求数据异常：" + f);
//                        Toast.makeText(RequestActivity.this, "请求出错：" + f, Toast.LENGTH_SHORT).show();
//                    }
//                });

                HashMap<String, String> params = new HashMap<>();
                params.put("key", "e3d610dde0076bbc53d1421b12cfee35");
                params.put("num", "5");
                OkHttpUtil.getInstance().doPost(url, params, new OkHttpUtil.DataCallBack() {
                    @Override
                    public void onSuccess(String s) {
                        main_txt.setText(s);
                    }

                    @Override
                    public void onFailure(String f) {
                        LogUtils.e("请求数据异常：" + f);
                        Toast.makeText(RequestActivity.this, "请求出错：" + f, Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }
}
