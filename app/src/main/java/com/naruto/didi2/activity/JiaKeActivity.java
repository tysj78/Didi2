package com.naruto.didi2.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JiaKeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv_content = new TextView(this);
        tv_content.setText("I am JiaKe Apk");
        setContentView(tv_content);
    }
}
