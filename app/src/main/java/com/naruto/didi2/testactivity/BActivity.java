package com.naruto.didi2.testactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.naruto.didi2.R;
import com.naruto.didi2.constant.Constants;


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
