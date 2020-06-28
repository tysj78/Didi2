package com.yangyong.didi2.dbdao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * xxx class
 *
 * @author yangyong
 * @date 2020/6/28/0028
 */

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "didi2.db";
    private static final int DATABASE_VERSION = 1001;
    public static final String LEGION_TABLE_NAME = "legion";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + LEGION_TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,level INTEGER,power INTEGER)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
