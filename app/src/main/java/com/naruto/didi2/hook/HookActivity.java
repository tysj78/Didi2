package com.naruto.didi2.hook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.naruto.didi2.R;

public class HookActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bt_hook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hook4);
        initView();
    }

    private void initView() {
        bt_hook = (Button) findViewById(R.id.bt_hook);

        bt_hook.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_hook:
                Toast.makeText(this, getContents(), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private String getContents() {
        return "欧拉欧拉欧拉";
    }
}
