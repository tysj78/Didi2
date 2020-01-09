package com.yangyong.didi2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class XrecyclerActivity extends AppCompatActivity implements View.OnClickListener {

    private Button gol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xrecycler);
        initView();
    }

    private void initView() {
        gol = (Button) findViewById(R.id.gol);

        gol.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gol:
                startActivity(new Intent(this, LeakActivity.class));
                break;
        }
    }
}
