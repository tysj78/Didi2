package com.yangyong.didi2.cp;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.yangyong.didi2.dbdao.DbHelper;
import com.yangyong.didi2.util.LogUtils;


public class MyContentProvider extends ContentProvider {
    private static final UriMatcher mMatcher;
    private static final String AUTOHORITY = "com.yangyong.mycp";
    private static final int LEGION_CODE = 101;

    static {
        mMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mMatcher.addURI(AUTOHORITY, "legion", LEGION_CODE);
    }

    private DbHelper dbHelper;
    private SQLiteDatabase mDatabase;

    //删
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        String tableName = getTableName(uri);
        return mDatabase.delete(tableName, selection, selectionArgs);
    }


    @Override
    public String getType(Uri uri) {
        return "";
    }

    @Nullable
    @Override
    public Bundle call(@NonNull String method, @Nullable String arg, @Nullable Bundle extras) {
        String key = extras.getString("key");
        switch (key){
            case "setPer":
                LogUtils.e("设置策略成功："+arg);
                break;
        }
        return super.call(method, arg, extras);
    }

    //增
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        String tableName = getTableName(uri);
        mDatabase.insert(tableName, null, values);
        return uri;
    }

    private String getTableName(Uri uri) {
        String tableName = "";
        switch (mMatcher.match(uri)) {
            case LEGION_CODE:
                tableName = DbHelper.LEGION_TABLE_NAME;
                break;
        }
        return tableName;
    }

    @Override
    public boolean onCreate() {
        LogUtils.e("MyContentProvider onCreate");
        //初始化数据库
        dbHelper = new DbHelper(getContext());
        mDatabase = dbHelper.getWritableDatabase();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        String tableName = getTableName(uri);
        return mDatabase.query(tableName, projection, selection, selectionArgs, null, null, null);
    }

    //改
    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        String tableName = getTableName(uri);
        return mDatabase.update(tableName, values, selection, selectionArgs);
    }
}
