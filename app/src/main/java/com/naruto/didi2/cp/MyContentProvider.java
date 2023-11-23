package com.naruto.didi2.cp;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.aixunyun.cybermdm.util.sm.SM;
import com.naruto.didi2.database.DbTable;
import com.naruto.didi2.dbdao.DbHelper;
import com.naruto.didi2.util.AppUtil;
import com.naruto.didi2.util.LogUtils;
import com.naruto.didi2.util.SpUtils;


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
        Bundle bundle = new Bundle();
        String key = extras.getString("key");
        switch (key) {
            case "setPer":
                LogUtils.e("设置策略成功：" + arg);
                String signal = extras.getString("signal");

                String s = SM.doSM4DecryptData(signal);
                String localSignal = (String) AppUtil.getInstance().getStringFromAssets(getContext(), "signal");

                LogUtils.e("应用签名：" + signal + "==" + s + "\n" + "本地签名：" + localSignal);

                if (s.equals(localSignal)) {
                    //签名验证成功
                    SpUtils.saveStringValue(getContext(), "start", "true");
                    bundle.putString("result", "true");
                    LogUtils.e("验签成功");
                } else {
                    SpUtils.saveStringValue(getContext(), "start", "false");
                    bundle.putString("result", "false");
                    LogUtils.e("验签失败");
                }
                break;
            case "com.didi2.task":
//                Toast.makeText(getContext(), "接收到：" + arg, Toast.LENGTH_SHORT).show();
                LogUtils.e("com.didi2.task");
                break;
        }
        return bundle;
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
                tableName = DbTable.LEGION_TABLE_NAME;
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
