package com.yangyong.didi2.util;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.yangyong.didi2.Constants;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteDatabaseHook;
import net.sqlcipher.database.SQLiteOpenHelper;

import java.io.File;
import java.io.IOException;


/**
 * Created by yangyong on 2019/12/27/0027.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String name = "didi2.db";
    //第三版数据库采用加密数据库
    private static final int version = 3;
    private static final String TAG = "yy";

    public DataBaseHelper(Context context) {
        super(context, name, null, version, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e(TAG, "DataBaseHelperonCreate: ");
        //建表
        String createTable = "create table user_didi(id INTEGER PRIMARY KEY,name varchar,pwd varchar,sex varchar(1))";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 更新
        Log.e(TAG, "DataBaseHelperonUpgrade: ");
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

    public static void encrypt(Context context, String dbName, String passphrase) {
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
    }


    /**
     * 加密数据库
     * @param encryptedName 加密后的数据库名称
     * @param decryptedName 要加密的数据库名称
     * @param key 密码
     */
    public void encrypt(Context context,String encryptedName,String decryptedName,String key) {
        try {
            File databaseFile = context.getDatabasePath(decryptedName);
            SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(databaseFile, "", null);//打开要加密的数据库

            /*String passwordString = "1234"; //只能对已加密的数据库修改密码，且无法直接修改为“”或null的密码
            database.changePassword(passwordString.toCharArray());*/

            File encrypteddatabaseFile = context.getDatabasePath(encryptedName);//新建加密后的数据库文件
            //deleteDatabase(SDcardPath + encryptedName);

            //连接到加密后的数据库，并设置密码
            database.rawExecSQL(String.format("ATTACH DATABASE '%s' as "+ encryptedName.split("\\.")[0] +" KEY '"+ key +"';", encrypteddatabaseFile.getAbsolutePath()));
            //输出要加密的数据库表和数据到加密后的数据库文件中
            database.rawExecSQL("SELECT sqlcipher_export('"+ encryptedName.split("\\.")[0] +"');");
            //断开同加密后的数据库的连接
            database.rawExecSQL("DETACH DATABASE "+ encryptedName.split("\\.")[0] +";");

            //打开加密后的数据库，测试数据库是否加密成功
            SQLiteDatabase encrypteddatabase = SQLiteDatabase.openOrCreateDatabase(encrypteddatabaseFile, key, null);
            //encrypteddatabase.setVersion(database.getVersion());
            encrypteddatabase.close();//关闭数据库

            database.close();
        } catch (Exception e) {
//            e.printStackTrace();
            Log.e(TAG, "encrypt异常: " +e.getMessage());
        }
    }

}
