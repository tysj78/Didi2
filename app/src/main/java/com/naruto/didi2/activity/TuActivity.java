package com.naruto.didi2.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.naruto.didi2.R;


public class TuActivity extends AppCompatActivity {

    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tu);
        initView();
        String url = getIntent().getStringExtra("url");
//        String url = "";
        if (!url.isEmpty()) {
            Glide.with(this).load(url).into(img);
        }
    }

    private void initView() {
        img = (ImageView) findViewById(R.id.img);
    }
}
