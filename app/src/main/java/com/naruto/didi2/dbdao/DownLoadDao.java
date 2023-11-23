package com.naruto.didi2.dbdao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.naruto.didi2.MyApp;
import com.naruto.didi2.bean.ThreadInfo;
import com.naruto.didi2.util.LogUtils;

import java.util.ArrayList;

/**
 * 线程表操作类
 *
 * @author yangyong
 * @date 2020/7/2/0002
 */

public class DownLoadDao {

    private final String THREAD_INFO = "thread_info";
    private static DownLoadDao instance = new DownLoadDao(MyApp.getContext());
    private DownLoadHelper downLoadHelper;

    private DownLoadDao(Context context) {
        downLoadHelper = new DownLoadHelper(context);
    }

//    public void closeDb() {
//        downLoadHelper = null;
//        mDatabase = null;
//    }

    public static DownLoadDao getInstance() {
        return instance;
    }

    //增
    public synchronized void addThread(ThreadInfo info) {
        LogUtils.e("开始写入数据："+info.getUrl());
        SQLiteDatabase mDatabase = downLoadHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("url", info.getUrl());
        values.put("start", info.getStart());
        values.put("end", info.getEnd());
        values.put("finished", info.getFinished());
        mDatabase.insert(THREAD_INFO, null, values);
        LogUtils.e("插入数据完成:" + info.getUrl() + mDatabase);
    }

    //删
    public void delete(ThreadInfo info) {
        SQLiteDatabase mDatabase = downLoadHelper.getWritableDatabase();
        mDatabase.delete(THREAD_INFO, "t_id = ? and url=?", new String[]{info.getT_id() + "", info.getUrl()});
    }

    //删
    public void deleteAll() {
        SQLiteDatabase mDatabase = downLoadHelper.getWritableDatabase();
        mDatabase.delete(THREAD_INFO, null, null);
    }

    //改
    public void update(ThreadInfo info) {
        SQLiteDatabase mDatabase = downLoadHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("url", info.getUrl());
        values.put("start", info.getStart());
        values.put("end", info.getEnd());
        values.put("finished", info.getFinished());
        mDatabase.update(THREAD_INFO, values, "url=?", new String[]{info.getUrl()});
        LogUtils.e("修改数据完成："+info.getStart());
    }

    //查
    public synchronized boolean exists(String url) {
        Cursor cursor = null;
        try {
            SQLiteDatabase mDatabase = downLoadHelper.getWritableDatabase();
            cursor = mDatabase.query(THREAD_INFO, null, "url=?", new String[]{url}, null, null, null);
            return cursor.moveToNext();
        } catch (Exception e) {
            LogUtils.e("Exception: " + e.toString());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return false;
    }

    //查
    public ThreadInfo select(String url) {
        Cursor cursor = null;
        ThreadInfo threadInfo = null;
        try {
            SQLiteDatabase mDatabase = downLoadHelper.getWritableDatabase();
            cursor = mDatabase.query(THREAD_INFO, null, "url=?", new String[]{url}, null, null, null);
            if (cursor.moveToNext()) {
                int t_id = cursor.getInt(0);
                String f_url = cursor.getString(1);
                int start = cursor.getInt(2);
                int end = cursor.getInt(3);
                long finished = cursor.getLong(4);
                threadInfo = new ThreadInfo(t_id, f_url, start, end, finished);
                LogUtils.e(threadInfo.toString());
            }
        } catch (Exception e) {
            LogUtils.e("Exception: " + e.toString());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return threadInfo;
    }

    //查
    public ArrayList<ThreadInfo> selectAll() {
        LogUtils.e("读取数据库信息...");
        Cursor cursor = null;
        ArrayList<ThreadInfo> threadInfos = new ArrayList<>();
        ThreadInfo threadInfo = null;
        try {
            SQLiteDatabase mDatabase = downLoadHelper.getWritableDatabase();
            cursor = mDatabase.query(THREAD_INFO, null, null, null, null, null, null);
            int count = cursor.getCount();
            LogUtils.e("记录数量："+count);
            while (cursor.moveToNext()) {
                int t_id = cursor.getInt(0);
                String f_url = cursor.getString(1);
                int start = cursor.getInt(2);
                int end = cursor.getInt(3);
                long finished = cursor.getLong(4);
                threadInfo = new ThreadInfo(t_id, f_url, start, end, finished);
                threadInfos.add(threadInfo);
                LogUtils.e(threadInfo.toString());
            }
        } catch (Exception e) {
            LogUtils.e("Exception: " + e.toString());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return threadInfos;
    }
}
