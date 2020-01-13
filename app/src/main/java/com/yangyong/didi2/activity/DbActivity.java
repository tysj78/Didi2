package com.yangyong.didi2.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.yangyong.didi2.R;
import com.yangyong.didi2.bean.User;
import com.yangyong.didi2.dbdao.UserDao;

import net.sqlcipher.database.SQLiteDatabase;

public class DbActivity extends AppCompatActivity implements View.OnClickListener {

    private Button adduser;
    private Button queryuser;
    private UserDao userDao;
    private Button initdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);
        initView();
        dbOpen();
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.adduser:
//                userDao.addUser(new User("天天", "51230"));
                userDao.addUser(new User("井野", "51230", "女"));
                userDao.addUser(new User("李洛克", "51230", "男"));
                userDao.addUser(new User("春野樱", "51230", "女"));
                break;
            case R.id.queryuser:
                userDao.queryAllUser();
                break;
            case R.id.initdb:
//                loadJiaMi();
                break;
        }
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
