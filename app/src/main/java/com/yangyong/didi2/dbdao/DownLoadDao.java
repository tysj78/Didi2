package com.yangyong.didi2.dbdao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yangyong.didi2.bean.ThreadInfo;
import com.yangyong.didi2.util.LogUtils;

import java.util.ArrayList;

/**
 * 线程表操作类
 *
 * @author yangyong
 * @date 2020/7/2/0002
 */

public class DownLoadDao {

    private final SQLiteDatabase mDatabase;
    private final String THREAD_INFO = "thread_info";

    public DownLoadDao(Context context) {
        DownLoadHelper downLoadHelper = new DownLoadHelper(context);
        mDatabase = downLoadHelper.getWritableDatabase();
    }

    //增
    public void addThread(ThreadInfo info) {
        ContentValues values = new ContentValues();
        values.put("url", info.getUrl());
        values.put("start", info.getStart());
        values.put("end", info.getEnd());
        values.put("finished", info.getFinished());
        mDatabase.insert(THREAD_INFO, null, values);
    }

    //删
    public void delete(ThreadInfo info) {
        mDatabase.delete(THREAD_INFO, "t_id = ? and url=?", new String[]{info.getT_id() + "", info.getUrl()});
    }

    //删
    public void deleteAll() {
        mDatabase.delete(THREAD_INFO, null, null);
    }

    //改
    public void update(ThreadInfo info) {
        ContentValues values = new ContentValues();
        values.put("url", info.getUrl());
        values.put("start", info.getStart());
        values.put("end", info.getEnd());
        values.put("finished", info.getFinished());
        mDatabase.update(THREAD_INFO, values, "url=?", new String[]{info.getUrl()});
    }

    //查
    public boolean exists(String url) {
        Cursor cursor = mDatabase.query(THREAD_INFO, null, "url=?", new String[]{url}, null, null, null);
        boolean b = cursor.moveToNext();
        cursor.close();
        return b;
    }

    //查
    public ThreadInfo select(String url) {
        Cursor cursor = null;
        ThreadInfo threadInfo = null;
        try {
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
        Cursor cursor = null;
        ArrayList<ThreadInfo> threadInfos = new ArrayList<>();
        ThreadInfo threadInfo = null;
        try {
            cursor = mDatabase.query(THREAD_INFO, null, null, null, null, null, null);
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
