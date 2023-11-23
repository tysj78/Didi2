package com.naruto.didi2.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.naruto.didi2.R;
import com.naruto.didi2.constant.Constants;

import com.naruto.didi2.util.MultiDexUtils;
import com.naruto.didi2.util.NetUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class ClipboardActivity extends BaseActivity implements View.OnClickListener {

    private Button main_move;
    private Button main_coppy;
    private Button main_add;
    private Button main_query;
    private static final String AUTHORITY = "com.yangyong.TestContentProvider";
    private static final Uri STUDENT_URI = Uri.parse("content://" + AUTHORITY + "/test");
    private ContentResolver contentResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clipboard);
        initView();
        contentResolver = getContentResolver();
//        SystemClock.sleep(2000);
        //com/yangyong/didi2/MyApp.class
//        com/yangyong/didi2/Main10Activity.class
        NetUtils.getInstance().uu();
    }

    public void onMove1(View view) {
//        ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
//        ClipData cd2 = cm.getPrimaryClip();
//        String str2 = cd2.getItemAt(0).getText().toString();
//        Toast.makeText(this, "获取到剪切板内容：" + str2, Toast.LENGTH_SHORT).show();


//        String s = NetUtils.getInstance(getApplicationContext()).name;
//        Log.e(Constants.TAG, "onClick: "+s );

        startActivity(new Intent(this, HookActivity.class));
    }


    private void coppyToSd() {
        try {
            MultiDexUtils dexUtils = new MultiDexUtils();
            List<String> des = dexUtils.getLoadedExternalDexClasses(this);
            Log.e(Constants.TAG, "coppyToSd: " + des.size());
            String sdCardDir = Environment.getExternalStorageDirectory().getAbsolutePath();
            File saveFile = new File(sdCardDir, "aaaa.txt");

            FileOutputStream outStream = new FileOutputStream(saveFile);
            outStream.write(listToString(des).getBytes());
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String listToString(List<String> stringList) {
        if (stringList == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean flag = false;
        for (String string : stringList) {
            if (flag) {
                result.append("\n");
            } else {
                flag = true;
            }
            result.append(string);
        }
        return result.toString();
    }

    private void initView() {
        main_move = (Button) findViewById(R.id.main_move);
        main_coppy = (Button) findViewById(R.id.main_coppy);

        main_coppy.setOnClickListener(this);
        main_add = (Button) findViewById(R.id.main_add);
        main_add.setOnClickListener(this);
        main_query = (Button) findViewById(R.id.main_query);
        main_query.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_coppy:
                coppyToSd();
                break;
            case R.id.main_add:
                contentResolver.insert(STUDENT_URI,null);
                break;
            case R.id.main_query:
                contentResolver.query(STUDENT_URI,null,null,null);
                break;
                default:
                    break;
        }
    }
}
