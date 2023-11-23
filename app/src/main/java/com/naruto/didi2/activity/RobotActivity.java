package com.naruto.didi2.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import com.naruto.didi2.R;
import com.naruto.didi2.bean.Robot;
import com.naruto.didi2.util.AesUtils;
import com.naruto.didi2.util.AppUtil;
import com.naruto.didi2.util.LogUtils;
import com.naruto.didi2.util.OkHttpUtil;
import com.naruto.didi2.util.SpUtils;

import java.util.HashMap;

public class RobotActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_say;
    private Button bt_send;
    private TextView tv_robot;

    private String url = "http://api.tianapi.com/txapi/robot/index";
    private StringBuffer mContent = new StringBuffer();
    private Context mContext;
    private long lastTime;
    private String question = "CDJYZVEo7o7CaZW5Qx+89UxPI6eIwNwbqxDSoSe1BtDOsgEEXGiNfbAt4B31VvwQ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robot);
        initView();
        mContext = this;
    }

    private void initView() {
        et_say = (EditText) findViewById(R.id.et_say);
        bt_send = (Button) findViewById(R.id.bt_send);

        bt_send.setOnClickListener(this);
        tv_robot = (TextView) findViewById(R.id.tv_robot);
        tv_robot.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_send:
                long currentTimeMillis = System.currentTimeMillis();
                if (currentTimeMillis - lastTime > 2000) {
                    submit();
                } else {
                    AppUtil.getInstance().toast("说话慢点哦");
                }
                break;
        }
    }

    private void submit() {
        // validate
        String say = et_say.getText().toString().trim();
        if (TextUtils.isEmpty(say)) {
            Toast.makeText(this, "想聊点什么呢", Toast.LENGTH_SHORT).show();
            return;
        }

        long longValue = SpUtils.getLongValue(this, SpUtils.RUNCOUNT);
        if (longValue >= 99) {
            AppUtil.getInstance().toast("今天小舞已经累了哦，明天再和小舞聊天吧");
            return;
        }

        HashMap<String, String> params = new HashMap<>();
        String decrypt = AesUtils.decrypt(question);
        params.put("key", decrypt);
        params.put("question", say);
        OkHttpUtil.getInstance().doPost(url, params, new OkHttpUtil.DataCallBack() {

            @Override
            public void onSuccess(String s) {
                Gson gson = new Gson();
                Robot robot = gson.fromJson(s, Robot.class);
                int code = robot.getCode();
                if (code == 200) {
                    long longValue = SpUtils.getLongValue(mContext, SpUtils.RUNCOUNT);
                    longValue++;
                    SpUtils.saveLongValue(mContext, SpUtils.RUNCOUNT, longValue);
                    Robot.NewslistBean newslistBean = robot.getNewslist().get(0);
                    String reply = newslistBean.getReply();
                    mContent.append(reply).append("\n");
                    tv_robot.setText(mContent.toString());
                    et_say.setText("");

                } else {
                    LogUtils.e("接口访问失败：" + s);
                }
                lastTime = System.currentTimeMillis();
            }

            @Override
            public void onFailure(String f) {
                LogUtils.e("接口访问失败：" + f);
                lastTime = System.currentTimeMillis();
            }
        });

    }
}
