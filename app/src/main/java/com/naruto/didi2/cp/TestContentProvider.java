package com.naruto.didi2.cp;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangyong on 2019/9/11/0011.
 */

public class TestContentProvider extends ContentProvider {
    //这里的AUTHORITY就是我们在AndroidManifest.xml中配置的authorities
    private static final String AUTHORITY = "com.yangyong.TestContentProvider";
    //匹配成功后的匹配码
    private static final int MATCH_CODE = 100;
    private static UriMatcher uriMatcher;

    static {
        //匹配不成功返回NO_MATCH(-1)
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        //添加我们需要匹配的uri
        uriMatcher.addURI(AUTHORITY, "test", MATCH_CODE);
    }

    private List<String> lists;
    private String TAG = "yy";

    @Override
    public boolean onCreate() {
        Log.e(TAG, "TestContentProvider_onCreate: " );
        lists = new ArrayList<>();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        int match = uriMatcher.match(uri);
        if (match == MATCH_CODE) {
//            Cursor cursor = new Cursor();
//            return cursor;
            for (String s : lists) {
                Log.e(TAG, "query: " + s);
            }
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        if (uriMatcher.match(uri) == MATCH_CODE) {
            lists.add("我叫张小凡");
            Log.e(TAG, "insert:成功");
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
