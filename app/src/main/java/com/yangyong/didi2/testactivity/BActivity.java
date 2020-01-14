package com.yangyong.didi2.testactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.yangyong.didi2.Constants;
import com.yangyong.didi2.R;

public class BActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(Constants.TAG, "BActivityonDestroy: " );
    }
}
