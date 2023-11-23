package com.naruto.didi2.database;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.naruto.didi2.R;
import com.naruto.didi2.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class StudentActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bt_student_add;
    private Button bt_student_delete;
    private Button bt_student_update;
    private Button bt_student_query;
    private Button bt_student_pl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        initView();
    }

    private void initView() {
        bt_student_add = (Button) findViewById(R.id.bt_student_add);
        bt_student_delete = (Button) findViewById(R.id.bt_student_delete);
        bt_student_update = (Button) findViewById(R.id.bt_student_update);
        bt_student_query = (Button) findViewById(R.id.bt_student_query);

        bt_student_add.setOnClickListener(this);
        bt_student_delete.setOnClickListener(this);
        bt_student_update.setOnClickListener(this);
        bt_student_query.setOnClickListener(this);
        bt_student_pl = (Button) findViewById(R.id.bt_student_pl);
        bt_student_pl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_student_add:
                Student student = new Student("yang", 26, "男", "程序员");
                StudentDaoImpl.getInstance(this).add(student);
                break;
            case R.id.bt_student_delete:
                StudentDaoImpl.getInstance(this).deleteAll();
                break;
            case R.id.bt_student_update:
                Student student1 = new Student("杨", 26, "男", "程序员");
                StudentDaoImpl.getInstance(this).updateByName("yang", student1);
                break;
            case R.id.bt_student_query:
                List<Student> students = StudentDaoImpl.getInstance(this).query();
                for (int i = 0; i < students.size(); i++) {
                    Student student2 = students.get(i);
                    LogUtils.e(student2.toString());
                }
                break;
            case R.id.bt_student_pl:
                List<Student> sts = new ArrayList<>();
                for (int i = 0; i < 100; i++) {
                    Student studenta = new Student("杨", 26, "男", "程序员");
                    sts.add(studenta);
                }
                StudentDaoImpl.getInstance(this).add(sts);
                break;
        }
    }
}
