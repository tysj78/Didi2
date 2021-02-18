package com.yangyong.didi2.activity;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.yangyong.didi2.R;
import com.yangyong.didi2.bean.User;
import com.yangyong.didi2.dbdao.UserDao;
import com.yangyong.didi2.util.FileUtils;
import com.yangyong.didi2.util.PermissionUtils;


import io.reactivex.functions.Consumer;

public class DbActivity extends AppCompatActivity implements View.OnClickListener {

    private Button adduser;
    private Button queryuser;
    private UserDao userDao;
    private Button initdb;
    private Button deleteuser;
    private Button newqueryuser;
    private String[] strings = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);
        initView();
        dbOpen();
        PermissionUtils.requestPermissions(this, strings, new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {

            }
        });
    }

    private void dbOpen() {
        userDao = new UserDao(this);
    }

    private void initView() {
        adduser = (Button) findViewById(R.id.adduser);
        queryuser = (Button) findViewById(R.id.queryuser);

        adduser.setOnClickListener(this);
        queryuser.setOnClickListener(this);
        initdb = (Button) findViewById(R.id.initdb);
        initdb.setOnClickListener(this);
        deleteuser = (Button) findViewById(R.id.deleteuser);
        deleteuser.setOnClickListener(this);
        newqueryuser = (Button) findViewById(R.id.newqueryuser);
        newqueryuser.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.adduser:
                userDao.addUser(new User("天天", "51230", "女"));
                userDao.addUser(new User("井野", "51230", "女"));
                userDao.addUser(new User("李洛克", "51230", "男"));
                userDao.addUser(new User("春野樱", "51230", "女"));
                break;
            case R.id.queryuser:
                userDao.queryAllUser();
                break;
            case R.id.initdb:
//                loadJiaMi();
                copy();
                break;
            case R.id.deleteuser:
                userDao.deleteUser();
                break;
            case R.id.newqueryuser:
                userDao.newQueryAllUser();
                break;
        }
    }

    private void copy() {
//        File databasePath = this.getDatabasePath("didi2.db");
//        String databasePath = "data/data/com.mobilewise.mobileware/database/emm.db";
        String databasePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/.emmconfigmanager/files/f953786b77f8c0a5e941bdb356cb14cdfedcef5c3d631c057fad8adc4dd2bebf19.txt";
        String target = Environment.getExternalStorageDirectory().getAbsolutePath() + "/pic/f953786b77f8c0a5e941bdb356cb14cdfedcef5c3d631c057fad8adc4dd2bebf19.txt";
        FileUtils.copyFile(databasePath, target);
    }
//    private void loadJiaMi() {
//        new Thread(
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            SQLiteDatabase.loadLibs(DbActivity.this);
//                            userDao = new UserDao(DbActivity.this);
//                        } catch (Exception e) {
//                            Log.e(Constants.TAG, "初始化加密数据库异常: " + e.getMessage());
//                        }
//                    }
//                }
//        ).start();
//    }
}
