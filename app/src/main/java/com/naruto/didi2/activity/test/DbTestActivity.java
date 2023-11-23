package com.naruto.didi2.activity.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.naruto.didi2.R;
import com.naruto.didi2.bean.Legion;
import com.naruto.didi2.dbdao.LegionDao;

public class DbTestActivity extends AppCompatActivity implements View.OnClickListener {

    private Button add;
    private Button query;
    private Button delete_t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_test);
        initView();

    }

    private void initView() {
        add = (Button) findViewById(R.id.add);
        query = (Button) findViewById(R.id.query);
        delete_t = (Button) findViewById(R.id.delete_t);

        add.setOnClickListener(this);
        query.setOnClickListener(this);
        delete_t.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                Legion legion = new Legion();
                legion.setName("鸣人");
                legion.setHtian(300);
                LegionDao.getInstance().add(legion);
                break;
            case R.id.query:
                LegionDao.getInstance().query();
                break;
            case R.id.delete_t:
                LegionDao.getInstance().delete();
                addData();
                break;
        }
    }

    private void addData() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Legion legion = new Legion();
                        legion.setName(System.currentTimeMillis() + "");
                        LegionDao.getInstance().add(legion);


                    }
                }
        ).start();
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Legion legion = new Legion();
                        legion.setName(System.currentTimeMillis() + "");
                        LegionDao.getInstance().add(legion);

                    }
                }
        ).start();
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Legion legion = new Legion();
                        legion.setName(System.currentTimeMillis() + "");
                        LegionDao.getInstance().add(legion);

                    }
                }
        ).start();
    }
}
