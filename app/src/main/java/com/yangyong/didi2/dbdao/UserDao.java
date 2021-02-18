package com.yangyong.didi2.dbdao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.yangyong.didi2.constant.Constants;
import com.yangyong.didi2.bean.User;
import com.yangyong.didi2.util.DataBaseHelper;
import com.yangyong.didi2.util.LogUtils;



import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangyong on 2019/12/27/0027.
 */

public class UserDao {

    private static final String TAG = "yylog";
    private SQLiteDatabase db;
    private String t_name = "user_didi";

    public UserDao(Context context) {

//        DBencrypt.getInstences().encrypt(context,"didi20700","didi2.db");

        try {
//            Log.e(TAG, "UserDao获取加密数据库对象: ");
            Log.e(TAG, "UserDao获取普通数据库对象: ");
//            db = new DataBaseHelper(context).getWritableDatabase("didi20700");
            db = new DataBaseHelper(context).getWritableDatabase();
        } catch (Exception e) {
            LogUtils.e("打开加密数据库异常:"+e.toString());
        }
    }

    //增
    public void addUser(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", user.getName());
        contentValues.put("pwd", user.getPwd());
        contentValues.put("sex", user.getSex());
        db.insert(t_name, null, contentValues);
    }

    //删
    public void deleteUser(String id) {
        db.delete(t_name, "id=?", new String[]{id});
    }

    //改
    public void updateUser(User user, String id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", user.getName());
        contentValues.put("pwd", user.getPwd());
        contentValues.put("sex", user.getSex());
        db.update(t_name, contentValues, "id=?", new String[]{id});
    }

    //查
    public List<User> queryAllUser() {
        List<User> list = new ArrayList<>();
        String[] columns = {"name", "pwd", "sex"};
        Cursor cursor = null;
        try {
            cursor = db.query(t_name, columns, null, null, null, null, null);
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String password = cursor.getString(cursor.getColumnIndex("pwd"));
                String sex = cursor.getString(cursor.getColumnIndex("sex"));

                User user = new User(name, password, sex);
                Log.e(TAG, "queryAllUser: " + user.toString());
                list.add(user);
                ////new String();
            }
        } catch (Exception e) {
            Log.e(Constants.TAG, "Exception: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return list;
    }

    //查
    public List<User> newQueryAllUser() {
        List<User> list = new ArrayList<>();
        String[] columns = {"name", "pwd", "sex","age"};
        Cursor cursor = null;
        try {
            cursor = db.query(t_name, columns, null, null, null, null, null);
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String password = cursor.getString(cursor.getColumnIndex("pwd"));
                String sex = cursor.getString(cursor.getColumnIndex("sex"));
                String age = cursor.getString(cursor.getColumnIndex("age"));

                User user = new User(name, password, sex,age);
                Log.e(TAG, "queryAllUser: " + user.toString());
                list.add(user);
                ////new String();
            }
        } catch (Exception e) {
            Log.e(Constants.TAG, "Exception: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return list;
    }

    //查 根据用户名获得密码
    public String queryPwdByUserName(String name) {
        Cursor cursor = null;
        String pwd = "";
        try {
            cursor = db.query(t_name, new String[]{"pwd"}, "name=?", new String[]{name}, null, null, null);
            if (cursor.moveToNext()) {
                pwd = cursor.getString(cursor.getColumnIndex("pwd"));
            }
        } catch (Exception e) {
            Log.e(Constants.TAG, "Exception: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        Log.e(TAG, "queryPwdByUserName: " + pwd);
        return pwd;
    }

    //删
    public void deleteUser() {
        db.delete(t_name, null,null);
    }
}
