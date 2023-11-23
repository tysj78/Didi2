package com.naruto.didi2.dbdao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.naruto.didi2.MyApp;
import com.naruto.didi2.bean.Legion;
import com.naruto.didi2.database.DbTable;
import com.naruto.didi2.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 军团表操作 class
 *
 * @author yangyong
 * @date 2021/6/30/0030
 */

public class LegionDao {

    private DbHelper mDbHelper;

    private LegionDao() {
        mDbHelper = new DbHelper(MyApp.getContext());
    }

    public static LegionDao getInstance() {
        return Inner.INSTANCE;
    }

    private static class Inner {
        private static final LegionDao INSTANCE = new LegionDao();
    }

    public void add(Legion legion) {
        try {
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", legion.getName());
            contentValues.put("level", legion.getLevel());
            contentValues.put("power", legion.getPower());
            contentValues.put("htian", legion.getHtian());
            db.insert(DbTable.LEGION_TABLE_NAME, null, contentValues);
            LogUtils.e("插入数成功：" + legion.toString());
        } catch (Exception e) {
            LogUtils.e("Exception: " + e.toString());
        }
    }

    public void delete() {
        try {
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            db.delete(DbTable.LEGION_TABLE_NAME, null, null);
            LogUtils.e("删除数据成功");
        } catch (Exception e) {
            LogUtils.e("Exception: " + e.toString());
        }
    }

    public void update() {

    }

    public List<Legion> query() {
        List<Legion> list = new ArrayList<>();
        String[] columns = {"name", "level", "power"};
        Cursor cursor = null;
        try {
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            cursor = db.query(DbTable.LEGION_TABLE_NAME, null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                int level = cursor.getInt(cursor.getColumnIndex("level"));
                int power = cursor.getInt(cursor.getColumnIndex("power"));
                int htian = cursor.getInt(cursor.getColumnIndex("htian"));

                Legion user = new Legion();
                user.setName(name);
                user.setLevel(level);
                user.setPower(power);
                user.setHtian(htian);
                LogUtils.e("queryAllUser: " + user.toString());
                list.add(user);
            }
        } catch (Exception e) {
            LogUtils.e("Exception: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        LogUtils.e("查询数量为：" + list.size());

        return list;
    }


    public long queryAppClear(String id) {
        Cursor cursor = null;
        try {
            SQLiteDatabase mDb = mDbHelper.getWritableDatabase();
            cursor = mDb.query("fs_clear_data", null, "type_id=?", new String[]{id}, null, null, null);
            if (cursor.moveToNext()){
                return cursor.getInt(cursor.getColumnIndex("time"));
            }
        } catch (Exception e) {
            LogUtils.e("Exception: " + e.toString());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return 0;
    }

    public void addAppClear(String id,long time) {
        try {
            SQLiteDatabase mDb = mDbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("type_id",id);
            contentValues.put("time", time);
            mDb.insert("fs_clear_data", null, contentValues);
        } catch (Exception e) {
            LogUtils.e("Exception: " + e.toString());
        }
    }

    public void updateAppClear(String id,long time) {
        SQLiteDatabase mDb = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("time", time);
        mDb.update("fs_clear_data", values, "type_id=?", new String[]{id});
    }
}
