package com.yangyong.didi2;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class Main6Activity extends AppCompatActivity implements View.OnClickListener {

    private Button main_b;
    private Button main_move;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        initView();

    }

    private void initView() {
        main_b = (Button) findViewById(R.id.main_b);

        main_b.setOnClickListener(this);
        main_move = (Button) findViewById(R.id.main_move);
        main_move.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_b:
                File[] files;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    files = getExternalFilesDirs(Environment.MEDIA_MOUNTED);
                    for (File file : files) {
                        Log.e("yy", file.getAbsolutePath());
                    }
                    String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
                    Log.e("yy", "getExternalStorageDirectory:" + absolutePath);
                }
                break;
            case R.id.main_move:
                break;
        }
    }

    public void onMove(View view) {
//        File dexOutputDir = getDir("dex1", 0);
        String dexOutputDir = Environment.getExternalStorageDirectory() + File.separator + "testjar/dex1";
        String dexPath = Environment.getExternalStorageDirectory() + File.separator + "testjar/out.jar";
        DexClassLoader loader =
                new DexClassLoader(dexPath, dexOutputDir, null, getClassLoader());
        try {
            Class clz = loader.loadClass("com.yangyong.didi2.DexRes");
            Method dexRes = clz.getDeclaredMethod("getString");
            Toast.makeText(this, (CharSequence) dexRes.invoke(clz.newInstance()), Toast.LENGTH_LONG)
                    .show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
