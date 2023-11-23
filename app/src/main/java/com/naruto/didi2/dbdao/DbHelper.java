package com.naruto.didi2.dbdao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.naruto.didi2.database.DbTable;
import com.naruto.didi2.util.LogUtils;


/**
 * xxx class
 * 数据库更新
 *
 * @author 数据库帮助类
 * @date 2020/6/28/0028
 */

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "didi2.db";
    private static final int DATABASE_VERSION = 1003;//最新的数据库版本

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        LogUtils.e("DbHelper onOpen");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        LogUtils.e("DbHelper onCreate");
        int initVersionCode = 1000;//数据库初始版本，不要修改，后面更新版本在此基础上增加
        String sql = "CREATE TABLE IF NOT EXISTS " + DbTable.LEGION_TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT,name nvarchar(20),level INTEGER,power INTEGER)";
        db.execSQL(sql);
        onUpgrade(db, initVersionCode, DATABASE_VERSION);//主动调用是解决应用卸载重装后不走onUpgrade内语句问题
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //应用覆盖安装进入这里，oldVersion记录了上个版本，通过循环将数据库版本更新到最新版
        for (int i = oldVersion; i < newVersion; i++) {
            LogUtils.e("DbHelper onUpgrade");
            switch (i + 1) {
                case 1001:
                    updateVersion1001(db);
                    break;
                case 1002:
                    updateVersion1002(db);
                    break;
                case 1003:
                    updateVersion1003(db);
                    break;
            }
        }
    }

    //创建user表
    private void updateVersion1003(SQLiteDatabase db) {
        LogUtils.e("updateVersion1003");
        String create_user = "create table if not exists user(id INTEGER PRIMARY KEY AUTOINCREMENT, name nvarchar(20))";
        db.execSQL(create_user);
    }

    //更新到版本1001，增加team，age字段
    private void updateVersion1001(SQLiteDatabase database) {
        try {
            LogUtils.e("updateVersion1001");
            String sql = "alter table legion add column team nvarchar(20)";
            String sql1 = "alter table legion add column age integer";
            database.execSQL(sql);
            database.execSQL(sql1);
        } catch (Exception e) {

            LogUtils.e("Exception: " + e.toString());
        }
    }

    //更新到版本1002，增加htian字段
    private void updateVersion1002(SQLiteDatabase database) {
        try {
            LogUtils.e("updateVersion1002");
            String sql = "alter table legion add column htian integer";
            database.execSQL(sql);
        } catch (Exception e) {
            LogUtils.e("Exception: " + e.toString());
        }
    }
}
