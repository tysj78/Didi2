package com.naruto.didi2.activity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


import com.naruto.didi2.R;
import com.naruto.didi2.service.MyIntentService;
import com.naruto.didi2.util.LogUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class CpActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bt_add;
    private Button bt_delete;
    private Button bt_update;
    private Button bt_query;
    private ContentResolver contentResolver;
    private Uri uri;
    private Button bt_start;
    private Button bt_add1;
    private Button bt_query1;
    private Button bt_fs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cp);
        initView();
        contentResolver = getContentResolver();
        uri = Uri.parse("content://com.yangyong.mycp/legion");
    }

    private void initView() {
        bt_add = (Button) findViewById(R.id.bt_add);
        bt_delete = (Button) findViewById(R.id.bt_delete);
        bt_update = (Button) findViewById(R.id.bt_update);
        bt_query = (Button) findViewById(R.id.bt_query);

        bt_add.setOnClickListener(this);
        bt_delete.setOnClickListener(this);
        bt_update.setOnClickListener(this);
        bt_query.setOnClickListener(this);
        bt_start = (Button) findViewById(R.id.bt_start);
        bt_start.setOnClickListener(this);
        bt_add1 = (Button) findViewById(R.id.bt_add1);
        bt_add1.setOnClickListener(this);
        bt_query1 = (Button) findViewById(R.id.bt_query1);
        bt_query1.setOnClickListener(this);
        bt_fs = (Button) findViewById(R.id.bt_fs);
        bt_fs.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_add:
                ContentValues contentValues = new ContentValues();
                contentValues.put("name", "风不会停息");
                contentValues.put("level", 31);
                contentValues.put("power", 151563);
                contentResolver.insert(uri, contentValues);
                break;
            case R.id.bt_delete:

                break;
            case R.id.bt_update:

                break;
            case R.id.bt_query:
                Cursor cursor = contentResolver.query(uri, null, null, null, null);
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(0);
                    String name = cursor.getString(1);
                    int level = cursor.getInt(2);
                    int power = cursor.getInt(3);
                    LogUtils.e("id:" + id + "==name:" + name + "==level:" + level + "==power:" + power);
                }
                if (cursor != null) {
                    cursor.close();
                }
                break;
            case R.id.bt_start:
                Intent intent = new Intent(this, MyIntentService.class);
                intent.setAction("com.didi2.task1");
                startService(intent);
                intent.setAction("com.didi2.task2");
                startService(intent);
                intent.setAction("com.didi2.task3");
                startService(intent);
                break;
            case R.id.bt_add1:
                ContentValues values = new ContentValues();
                values.put("name", "安林星");
                values.put("level", 39);
                values.put("power", 300000);
                values.put("team", "火毒队");
                values.put("htian", 174);
                contentResolver.insert(uri, values);
                break;
            case R.id.bt_query1:
                Cursor legion_cursor = contentResolver.query(uri, null, null, null, null);
                while (legion_cursor.moveToNext()) {
                    int id = legion_cursor.getInt(0);
                    String name = legion_cursor.getString(1);
                    int level = legion_cursor.getInt(2);
                    int power = legion_cursor.getInt(3);
                    String team = legion_cursor.getString(4);
                    int huangtian = legion_cursor.getInt(5);
                    LogUtils.e("id:" + id + "==name:" + name + "==level:" + level + "==power:" + power + "==team:" + team + "==hungtian:" + huangtian);
                }
                if (legion_cursor != null) {
                    legion_cursor.close();
                }
                break;
            case R.id.bt_fs:
                fs();
                break;
        }
    }

    private void fs() {
//        private String name;
//        private int level;
//        private int power;
//        private String team;
//        private int htian;
        try {
            //1.拿到class对象
            Class<?> aClass = Class.forName("com.naruto.didi2.bean.Legion");
            Constructor<?> constructor = aClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            Object legion = constructor.newInstance();
            Field name = aClass.getDeclaredField("name");
            Field level = aClass.getDeclaredField("level");
            Field power = aClass.getDeclaredField("power");
            Field team = aClass.getDeclaredField("team");
            Field htian = aClass.getDeclaredField("htian");

            name.setAccessible(true);
            level.setAccessible(true);
            power.setAccessible(true);
            team.setAccessible(true);
            htian.setAccessible(true);


            name.set(legion,"安林星");
            level.set(legion,39);
            power.set(legion,330000);
            team.set(legion,"火烧队");
            htian.set(legion,174);

            //2.反射私有方法
            Method printInfo = aClass.getDeclaredMethod("printInfo");
            printInfo.setAccessible(true);
            String invoke = (String) printInfo.invoke(legion);
            LogUtils.e(invoke);
        } catch (Exception e) {
            LogUtils.e(e.toString());
            e.printStackTrace();
        }
    }
}
