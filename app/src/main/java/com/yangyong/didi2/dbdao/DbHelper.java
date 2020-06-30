package com.yangyong.didi2.dbdao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.yangyong.didi2.util.LogUtils;


/**
 * xxx class
 *
 * @author yangyong
 * @date 2020/6/28/0028
 */

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "didi2.db";
    private static final int DATABASE_VERSION = 1003;
    public static final String LEGION_TABLE_NAME = "legion";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        LogUtils.e("DbHelper onCreate");
        int initVersionCode = 1001;
        String sql = "CREATE TABLE IF NOT EXISTS " + LEGION_TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,level INTEGER,power INTEGER)";
        db.execSQL(sql);
        onUpgrade(db, initVersionCode, DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (int i = oldVersion; i < newVersion; i++) {
            LogUtils.e("DbHelper onUpgrade");
            switch (i) {
                case 1001:
                    updateVersion2(db);
                    break;
                case 1002:
                    updateVersion3(db);
                    break;
            }
        }
    }

    //更新第二版
    private void updateVersion2(SQLiteDatabase database) {
        try {
            LogUtils.e("updateVersion2");
            String sql = "alter table legion add column team varchar(100)";
            database.execSQL(sql);
        } catch (Exception e) {
            LogUtils.e("Exception: " + e.toString());
        }
    }

    //更新第三版
    private void updateVersion3(SQLiteDatabase database) {
        try {
            LogUtils.e("updateVersion3");
            String sql = "alter table legion add column htian integer";
            database.execSQL(sql);
        } catch (Exception e) {
            LogUtils.e("Exception: " + e.toString());
        }
    }
}
