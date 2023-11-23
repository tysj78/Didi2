package com.naruto.didi2.activity.two;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import com.naruto.didi2.R;
import com.naruto.didi2.adapter.FileAdapter;
import com.naruto.didi2.util.AppUtil;

import java.io.File;

import io.reactivex.functions.Consumer;

public class FileActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private Button bt_up;
    private ListView lv_content;
    private TextView tv_pah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
        initView();

        AppUtil.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                initFile();
            }
        });

    }

    private void initFile() {
        FileAdapter Adter = new FileAdapter(this);
        lv_content.setAdapter(Adter);
        lv_content.setOnItemClickListener(this);
//        String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String absolutePath = AppUtil.getInstance().getSdPath();
        Adter.scanFiles(absolutePath);
        bt_up.setOnClickListener(this);
    }

    private void initView() {
        bt_up = (Button) findViewById(R.id.bt_up);
        lv_content = (ListView) findViewById(R.id.lv_content);

        bt_up.setOnClickListener(this);
        tv_pah = (TextView) findViewById(R.id.tv_pah);
        tv_pah.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_up:
                FileAdapter ad = (FileAdapter) lv_content.getAdapter();
                if (ad.currPath.equals("/")) {
                    return;
                }
                File f = new File(ad.currPath);
                tv_pah.setText(f.getParent());
                ad.scanFiles(f.getParent());
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FileAdapter da = (FileAdapter) lv_content.getAdapter();
        File f = da.list.get(position);
        if (f.isDirectory()) {
            tv_pah.setText(f.getPath());
            da.scanFiles(f.getPath());
        }
    }
}
