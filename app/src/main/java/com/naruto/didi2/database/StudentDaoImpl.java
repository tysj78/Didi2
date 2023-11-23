package com.naruto.didi2.database;

import android.content.ContentValues;
import android.content.Context;

import com.naruto.didi2.util.LogUtils;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * xxx class
 * @author yangyong
 * @date 2021/4/1/0001
 */

public class StudentDaoImpl implements StudentDao {
    private static StudentDaoImpl instance;
    private SQLiteDatabase db;

    private StudentDaoImpl(Context context) {
        LogUtils.e("开始获取数据库对象..");
        long start = System.currentTimeMillis();
        StudentDbHelper studentDbHelper = new StudentDbHelper(context);
        db = studentDbHelper.getWritableDatabase("naruto");
        long stop = System.currentTimeMillis();
        LogUtils.e("打开数据库用时：" + (stop - start));
    }

    //单例，双重校检锁
    public static StudentDaoImpl getInstance(Context context) {
        if (instance == null) {
            synchronized (StudentDaoImpl.class) {
                if (instance == null) {
                    instance = new StudentDaoImpl(context);
                }
            }
        }
        return instance;
    }

    @Override
    public void add(Student student) {
        try {
            ContentValues values = new ContentValues();
            values.put("name", student.getName());
            values.put("age", student.getAge());
            values.put("gender", student.getGender());
            values.put("job", student.getJob());
            db.insert(DbTable.TABLE_NAME, null, values);
            LogUtils.e("插入数据完成:");
        } catch (Exception e) {
            LogUtils.e("Exception: " + e.toString());
        }
    }

    @Override
    public void add(List<Student> student) {
        if (student ==null || student.size() == 0) {
            return;
        }
        long start = System.currentTimeMillis();
        try {
            db.beginTransaction();
            for (int i = 0; i < student.size(); i++) {
                Student student1 = student.get(i);
                ContentValues values = new ContentValues();
                values.put("name", student1.getName());
                values.put("age", student1.getAge());
                values.put("gender", student1.getGender());
                values.put("job", student1.getJob());
                db.insert(DbTable.TABLE_NAME, null, values);
            }
            db.setTransactionSuccessful();
            LogUtils.e("批量插入数据完成:");
        } catch (Exception e) {
            LogUtils.e("Exception: " + e.toString());
        } finally {
            db.endTransaction();
        }
        long stop = System.currentTimeMillis();
        LogUtils.e("批量插入数据库用时：" + (stop - start));
    }

    @Override
    public void deleteAll() {
        try {
            db.delete(DbTable.TABLE_NAME, null, null);
            LogUtils.e("删除数据完成:");
        } catch (Exception e) {
            LogUtils.e("Exception: " + e.toString());
        }
    }

    @Override
    public void updateByName(String name, Student student) {
        try {
            ContentValues values = new ContentValues();
            values.put("name", student.getName());
            values.put("age", student.getAge());
            values.put("gender", student.getGender());
            values.put("job", student.getJob());
            db.update(DbTable.TABLE_NAME, values, "name=?", new String[]{name});
            LogUtils.e("更新数据完成:");
        } catch (Exception e) {
            LogUtils.e("Exception: " + e.toString());
        }
    }

    @Override
    public Student queryByName(String name) {
        Student student = null;
        Cursor cursor = null;
        try {
            cursor = db.query(DbTable.TABLE_NAME, null, "name=?", new String[]{name}, null, null, null);
            if (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String _name = cursor.getString(cursor.getColumnIndex("name"));
                String age = cursor.getString(cursor.getColumnIndex("age"));
                String gender = cursor.getString(cursor.getColumnIndex("gender"));
                String job = cursor.getString(cursor.getColumnIndex("job"));
                student = new Student(id, _name, Integer.parseInt(age), gender, job);
            }
            LogUtils.e("查询数据完成:");
        } catch (Exception e) {
            LogUtils.e("Exception: " + e.toString());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return student;
    }

    @Override
    public List<Student> query() {
        LogUtils.e("读取数据库信息...");
        Cursor cursor = null;
        List<Student> students = new ArrayList<>();
        try {
            cursor = db.query(DbTable.TABLE_NAME, null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String _name = cursor.getString(cursor.getColumnIndex("name"));
                String age = cursor.getString(cursor.getColumnIndex("age"));
                String gender = cursor.getString(cursor.getColumnIndex("gender"));
                String job = cursor.getString(cursor.getColumnIndex("job"));
                Student student = new Student(id, _name, Integer.parseInt(age), gender, job);
                students.add(student);
            }
        } catch (Exception e) {
            LogUtils.e("Exception: " + e.toString());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return students;
    }

}
