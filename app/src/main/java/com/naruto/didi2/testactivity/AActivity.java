package com.naruto.didi2.testactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.naruto.didi2.R;
import com.naruto.didi2.constant.Constants;


public class AActivity extends AppCompatActivity implements View.OnClickListener {

    private Button tiaob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(Constants.TAG, "AActivityonDestroy: ");
    }

    private void initView() {
        tiaob = (Button) findViewById(R.id.tiaob);

        tiaob.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tiaob:
                startActivity(new Intent(this, BActivity.class));
                break;
        }
    }
}
