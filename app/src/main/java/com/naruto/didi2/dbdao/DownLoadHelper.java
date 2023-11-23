package com.naruto.didi2.dbdao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.naruto.didi2.util.LogUtils;


/**
 * xxx class
 *
 * @author yangyong
 * @date 2020/7/2/0002
 */

public class DownLoadHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "download.db";
    private static final int DB_VERSION = 1;

    public DownLoadHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        LogUtils.e("DownLoadHelper: onCreate");
        String sql = "create table if not exists thread_info(t_id integer primary key autoincrement,url text,start integer,end integer,finished integer)";
        db.execSQL(sql);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        LogUtils.e("DownLoadHelper: onOpen");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LogUtils.e("DownLoadHelper: onUpgrade");
    }
}
