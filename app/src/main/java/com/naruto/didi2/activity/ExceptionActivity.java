package com.naruto.didi2.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.naruto.didi2.R;


public class ExceptionActivity extends AppCompatActivity implements View.OnClickListener {

    private Button throw_error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exception);
        initView();

    }

    private void initView() {
        throw_error = (Button) findViewById(R.id.throw_error);

        throw_error.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.throw_error:

                break;
        }
    }
}
