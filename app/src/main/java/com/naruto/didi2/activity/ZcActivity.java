package com.naruto.didi2.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.naruto.didi2.R;


public class ZcActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "yy";
    private EditText password;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zc);
        initView();


    }

    private void initView() {
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);

        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                Log.e(TAG, "start login: " );
                String s = password.getText().toString();
                try {
                    if (checkLogin(s)) {
                        Toast.makeText(ZcActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ZcActivity.this, "登录失败！", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e("yy", "Exception: " + e.toString());
                }
                break;
        }
    }

    private boolean checkLogin(String s) {
        if ("1596".equals(s)) {
            return true;
        }
        return false;
    }

    private void submit() {
        // validate
        String passwordString = password.getText().toString().trim();
        if (TextUtils.isEmpty(passwordString)) {
            Toast.makeText(this, "输入密码：", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something


    }
}
