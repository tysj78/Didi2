package com.naruto.didi2.database;

import android.content.Context;

import com.naruto.didi2.util.LogUtils;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

/**
 * 加密数据库帮助类 class
 *
 * @author yangyong
 * @date 2021/4/1/0001
 */

public class StudentDbHelper extends SQLiteOpenHelper {
    private static final String DBNAME = "didi2.db";
    private static final int DBVERSION = 1;

    public StudentDbHelper(Context context) {
        super(context, DBNAME, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        LogUtils.e("StudentDbHelper onCreate");
        String createTable="create table if not exists student(id integer primary key autoincrement," +
                "name text,age text,gender text,job text)";
        db.execSQL(createTable);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        LogUtils.e("StudentDbHelper onOpen");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LogUtils.e("StudentDbHelper onUpgrade");
    }
}
