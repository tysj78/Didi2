package com.naruto.didi2.dbdao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.naruto.didi2.constant.Constants;
import com.naruto.didi2.bean.User;
import com.naruto.didi2.util.DataBaseHelper;
import com.naruto.didi2.util.LogUtils;


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
            LogUtils.e("打开加密数据库异常:" + e.toString());
        }
    }

    //增
    public void addUser(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", user.getName());
        contentValues.put("pwd", user.getPwd());
        contentValues.put("sex", user.getSex());
        contentValues.put("groupid", user.getGroupId());
        contentValues.put("isupload", user.getIsUpload());
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

    //根据范围更新组id
    public void updateUserByLimit(String groupId, String offset) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("groupid", groupId);
//            db.update(t_name, contentValues, "limit 10 offset ?", new String[]{offset});

//            String sql="UPDATE user_didi SET groupid=12 ";
//            String sql="UPDATE user_didi SET groupid='13' WHERE id in (SELECT id FROM FROM user_didi LIMIT 10 offset 0)";
            String sql = "UPDATE user_didi SET groupid='" + groupId + "' WHERE id in (select t.id  from (select * from user_didi limit 10 OFFSET " + offset + ")as t)";
            db.execSQL(sql);
        } catch (Exception e) {
            LogUtils.e("Exception: " + e.toString());
        }
    }

    //根据范围更新上传状态
    public void updateUpLoadStatus(String offset) {
        try {
//            db.update(t_name, contentValues, "limit 10 offset ?", new String[]{offset});

//            String sql="UPDATE user_didi SET groupid=12 ";
//            String sql="UPDATE user_didi SET groupid='13' WHERE id in (SELECT id FROM FROM user_didi LIMIT 10 offset 0)";
            String sql = "UPDATE user_didi SET isupload='1' WHERE id in (select t.id  from (select * from user_didi limit 10 OFFSET " + offset + ")as t)";
            db.execSQL(sql);
        } catch (Exception e) {
            LogUtils.e("Exception: " + e.toString());
        }
    }

    //根据组id更新上传状态
    public void updateUpLoadStatusByGroupId(String gid) {
        try {
//            db.update(t_name, contentValues, "limit 10 offset ?", new String[]{offset});

//            String sql="UPDATE user_didi SET groupid=12 ";
//            String sql="UPDATE user_didi SET groupid='13' WHERE id in (SELECT id FROM FROM user_didi LIMIT 10 offset 0)";
//            String sql = "UPDATE user_didi SET isupload='1' WHERE id in (select t.id  from (select * from user_didi limit 10 OFFSET " + offset + ")as t)";
//            db.execSQL(sql);
            ContentValues contentValues = new ContentValues();
            contentValues.put("isupload", "1");
            db.update(t_name, contentValues, "groupid=?", new String[]{gid});
        } catch (Exception e) {
            LogUtils.e("Exception: " + e.toString());
        }
    }

    //查,排除上传状态为1的记录，因为存在上传成功，记录未删的情况，会导致重复上传
    public List<User> queryAllUser(boolean log) {
        List<User> list = new ArrayList<>();
        String[] columns = {"name", "pwd", "sex", "groupid", "isupload"};
        Cursor cursor = null;
        try {
            cursor = db.query(t_name, columns, "isupload!=?", new String[]{"1"}, null, null, null);
//            cursor = db.query(t_name, columns, null, null, null, null, null);
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String password = cursor.getString(cursor.getColumnIndex("pwd"));
                String sex = cursor.getString(cursor.getColumnIndex("sex"));
                String groupid = cursor.getString(cursor.getColumnIndex("groupid"));
                String isupload = cursor.getString(cursor.getColumnIndex("isupload"));

                User user = new User(name, password, sex);
                user.setGroupId(groupid);
                user.setIsUpload(isupload);
                if (log) {
                    Log.e(TAG, "queryAllUser: " + user.toString());
                }
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

    public List<User> queryAllUserExclude() {
        List<User> list = new ArrayList<>();
        String[] columns = {"name", "pwd", "sex", "groupid", "isupload"};
        Cursor cursor = null;
        try {
//            cursor = db.query(t_name, columns, "isupload != ?", new String[]{"1"}, null, null, null);
            cursor = db.query(t_name, columns, null, null, null, null, null);
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String password = cursor.getString(cursor.getColumnIndex("pwd"));
                String sex = cursor.getString(cursor.getColumnIndex("sex"));
                String groupid = cursor.getString(cursor.getColumnIndex("groupid"));
                String isupload = cursor.getString(cursor.getColumnIndex("isupload"));

                User user = new User(name, password, sex);
                user.setGroupId(groupid);
                user.setIsUpload(isupload);
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
        String[] columns = {"name", "pwd", "sex", "age"};
        Cursor cursor = null;
        try {
            cursor = db.query(t_name, columns, null, null, null, null, null);
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String password = cursor.getString(cursor.getColumnIndex("pwd"));
                String sex = cursor.getString(cursor.getColumnIndex("sex"));
                String age = cursor.getString(cursor.getColumnIndex("age"));

                User user = new User(name, password, sex, age);
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
        db.delete(t_name, null, null);
    }

    //根据组id删除记录
    public void deleteUserByGroupId(String gid) {
        db.delete(t_name, "groupid=?", new String[]{gid});
        LogUtils.e("根据组id删除成功");
    }

    //根据上传状态删除记录
    public void deleteUpUser() {
        db.delete(t_name, "isupload=?", new String[]{"1"});
    }
}
