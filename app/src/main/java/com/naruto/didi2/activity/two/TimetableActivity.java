package com.naruto.didi2.activity.two;

import android.Manifest;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.naruto.didi2.R;
import com.naruto.didi2.database.DbTable;
import com.naruto.didi2.dbdao.DbHelper;
import com.naruto.didi2.util.AppUtil;
import com.naruto.didi2.util.FileUtils;
import com.naruto.didi2.util.LogUtils;

import org.json.JSONObject;

import io.reactivex.functions.Consumer;

public class TimetableActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView author;
    private Button bt_storage;
    private DbHelper dbHelper;
    private SQLiteDatabase writableDatabase;
    private Button bt_select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        initView();

        initDb();
        requestP();
    }

    private void initDb() {
        dbHelper = new DbHelper(this);
        writableDatabase = dbHelper.getWritableDatabase();
    }

    private void initView() {
        author = (TextView) findViewById(R.id.author);
        bt_storage = (Button) findViewById(R.id.bt_storage);

        bt_storage.setOnClickListener(this);
        bt_select = (Button) findViewById(R.id.bt_select);
        bt_select.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_storage:
//                AppUtil.getInstance().getAvailDataSize();
//                AppUtil.getInstance().getSdPath(this);
//                AppUtil.getInstance().getSize(this);

//                ContentValues appwidget_info = new ContentValues();
//                appwidget_info.put("name", "鸣人");
//                appwidget_info.put("level", 96);
//                appwidget_info.put("power", 9600000);
//                appwidget_info.put("team", "第七班");
//                appwidget_info.put("age", 21);
//                writableDatabase.insert(DbHelper.LEGION_TABLE_NAME, null, appwidget_info);

//                json();
                FileUtils.getFile(this);
                break;
            case R.id.bt_select:
                try {
                    Cursor query = writableDatabase.query(DbTable.LEGION_TABLE_NAME, null, null, null, null, null, null);
                    while (query.moveToNext()) {
                        int t_id = query.getInt(0);
                        String name = query.getString(1);
                        int level = query.getInt(2);
                        int power = query.getInt(3);
                        String team = query.getString(4);
                        int age = query.getInt(5);
                        LogUtils.e(name + "==" + level + "==" + power + "==" + team + "==" + age);
                    }
                } catch (Exception e) {
                    LogUtils.e("Exception: " + e.toString());
                }


                break;
        }
    }

    private void requestP() {
        AppUtil.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
            }
        });
    }

    void json() {
        try {
            JSONObject destBehavoir = new JSONObject();
            destBehavoir.put("actionCode", "10020");
            destBehavoir.put("actionCode", "10021");
            destBehavoir.put("actionCode", "10020");
            String string = destBehavoir.toString();
            LogUtils.e(string);
        } catch (Exception e) {
            LogUtils.e("Exception: " + e.toString());
        }
    }
}
