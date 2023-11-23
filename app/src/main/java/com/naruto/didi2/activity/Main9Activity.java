package com.naruto.didi2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.naruto.didi2.R;


//import com.tinkerpatch.sdk.TinkerPatch;

public class Main9Activity extends AppCompatActivity implements View.OnClickListener {

    private Button main_bug;
    private Button main_pull;
    private Button main_go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main9);
        initView();
        ////new String();
    }

    private void initView() {
        main_bug = (Button) findViewById(R.id.main_bug);

        main_bug.setOnClickListener(this);
        main_pull = (Button) findViewById(R.id.main_pull);
        main_pull.setOnClickListener(this);
        main_go = (Button) findViewById(R.id.main_go);
        main_go.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_bug:
                int a = 666;
                int b = 1;
                Toast.makeText(this, (a / b) + "", Toast.LENGTH_SHORT).show();
                break;
            case R.id.main_pull:
//                TinkerPatch.with().fetchPatchUpdate(true);
                break;
            case R.id.main_go:
                startActivity(new Intent(this,Main10Activity.class));
                break;
        }
    }
}
