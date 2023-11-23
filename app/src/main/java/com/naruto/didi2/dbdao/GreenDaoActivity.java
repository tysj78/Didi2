package com.naruto.didi2.dbdao;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.naruto.didi2.R;
import com.naruto.didi2.greendao.bean.Student;
import com.naruto.didi2.greendao.dao.StudentDaoOpe;

import java.util.ArrayList;
import java.util.List;

public class GreenDaoActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bt_add_data;
    private Button bt_delete_data;
    private Button bt_update_data;
    private Button bt_query_data;
    private TextView tv_content;
    private List<Student> studentList = new ArrayList<>();
//    private StudentDao studentDao;
    //    private StudentDao studentDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green_dao);
        initView();
        initData();
    }

    private void initData() {
        for (int i = 0; i < 5; i++) {
            Student student = new Student((long) i, "yang" + i, 25);
            studentList.add(student);
        }
//        DaoMaster.DevOpenHelper mDevOpenHelper = new DaoMaster.DevOpenHelper(this, "test.db");
//        DaoSession mDaoSession = new DaoMaster(mDevOpenHelper.getEncryptedWritableDb("password")).newSession();
//        studentDao = mDaoSession.getStudentDao();
//        studentDao = ((MyApp) getApplication()).getDaoSession().getStudentDao();
    }

    private void initView() {
        bt_add_data = (Button) findViewById(R.id.bt_add_data);
        bt_delete_data = (Button) findViewById(R.id.bt_delete_data);
        bt_update_data = (Button) findViewById(R.id.bt_update_data);
        bt_query_data = (Button) findViewById(R.id.bt_query_data);
        tv_content = (TextView) findViewById(R.id.tv_content);

        bt_add_data.setOnClickListener(this);
        bt_delete_data.setOnClickListener(this);
        bt_update_data.setOnClickListener(this);
        bt_query_data.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_add_data:
                StudentDaoOpe.insertData(this, studentList);
//                studentDao.insertOrReplaceInTx(studentList);
//                Student student = new Student("鸣人", 26);
//
//                studentDao.insert(student);
                break;
            case R.id.bt_delete_data:
//                Student student = new Student((long) 5, "haung" + 5, 25);
//                /**
//                 * 根据特定的对象删除
//                 */
//                StudentDaoOpe.deleteData(this, student);
//                /**
//                 * 根据主键删除
//                 */
//                StudentDaoOpe.deleteByKeyData(this, 7);
//                StudentDaoOpe.deleteAllData(this);
//                studentDao.deleteAll();
                break;
            case R.id.bt_update_data:
                Student student1 = new Student((long) 2, "haungxiaoguo", 16516);
                StudentDaoOpe.updateData(this, student1);
                break;
            case R.id.bt_query_data:
                List<Student> students = StudentDaoOpe.queryAll(this);
//                List<Student> students = studentDao.queryBuilder().build().list();
                StringBuffer stringBuffer = new StringBuffer();
                for (int i = 0; i < students.size(); i++) {
                    stringBuffer.append(students.get(i).toString()).append("\n");
                }
                tv_content.setText(stringBuffer);
                break;
        }
    }
}
