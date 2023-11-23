package com.naruto.didi2.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;






/**
 * Created by yangyong on 2019/12/27/0027.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String name = "didi2.db";
    //第三版数据库采用加密数据库
    private static final int version = 1;
    private static final String TAG = "yylog";

    public DataBaseHelper(Context context) {
        super(context, name, null, version, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e(TAG, "DataBaseHelperonCreate: ");
        int initVersion = 1;
        //建表
        String createTable = "create table user_didi(id INTEGER PRIMARY KEY,name varchar,pwd varchar,sex varchar(1),groupid varchar(255),isupload varchar(1))";
        db.execSQL(createTable);
        onUpgrade(db, initVersion, version);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 更新
        Log.e(TAG, "DataBaseHelperonUpgrade: ");
        for (int i = oldVersion; i < newVersion; i++) {
            switch (i) {
                case 1:
                    onUpgrade2(db);
                    break;
                case 2:

                    break;
            }
        }
//        if (oldVersion == 1) {
//            String sql = "alter table user_didi add sex varchar(1)";
//            db.execSQL(sql);
//            Log.e(TAG, "onUpgrade: 覆盖更新，更新数据库到2.0版本" );
//            ContentValues contentValues = new ContentValues();
//            contentValues.put("name","新增默认值");
//            contentValues.put("pwd","新增默认值");
//            contentValues.put("sex","新");
//            db.insert("user_didi",null,contentValues);
//            Log.e(TAG, "onUpgrade插入新增数据: ");
//        }

    }

    /**
     * 更新到版本2
     */
    private void onUpgrade2(SQLiteDatabase db) {
        String sql = "alter table user_didi add age varchar(3)";
            db.execSQL(sql);
            Log.e(TAG, "onUpgrade: 覆盖更新，更新数据库到2.0版本" );
            ContentValues contentValues = new ContentValues();
            contentValues.put("name","新增默认值");
            contentValues.put("pwd","新增默认值");
            contentValues.put("sex","新增默认值");
            contentValues.put("age","19");
            db.insert("user_didi",null,contentValues);
            Log.e(TAG, "onUpgrade插入新增数据: ");
    }

    /**
     * 加密
     * @param context
     * @param dbName
     * @param passphrase
     */
    /*public static void encrypt(Context context, String dbName, String passphrase) {
        try {
            File originalFile = context.getDatabasePath(dbName);
            if (originalFile.exists()) {
                File newFile = File.createTempFile("sqlcipherutils", "tmp", context.getCacheDir());
                SQLiteDatabase db = SQLiteDatabase.openDatabase(originalFile.getAbsolutePath(), "", null, SQLiteDatabase.OPEN_READWRITE);
                db.rawExecSQL(String.format("ATTACH DATABASE '%s' AS encrypted KEY '%s';", newFile.getAbsolutePath(), passphrase));
                db.rawExecSQL("SELECT sqlcipher_export('encrypted')");
                db.rawExecSQL("DETACH DATABASE encrypted;");
                int version = db.getVersion();
                db.close();
                db = SQLiteDatabase.openDatabase(newFile.getAbsolutePath(), passphrase, null, SQLiteDatabase.OPEN_READWRITE);
                db.setVersion(version);
                db.close();
                originalFile.delete();
                newFile.renameTo(originalFile);
            }
        } catch (Exception e) {
            Log.e(Constants.TAG, "Exception: " + e.getMessage());
        }
    }*/

}
