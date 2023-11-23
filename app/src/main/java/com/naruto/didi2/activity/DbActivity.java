package com.naruto.didi2.activity;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.naruto.didi2.R;
import com.naruto.didi2.bean.User;
import com.naruto.didi2.dbdao.UserDao;
import com.naruto.didi2.dbdao.UserUploadListener;
import com.naruto.didi2.util.AppUtil;
import com.naruto.didi2.util.EmulatorCheckUtil;
import com.naruto.didi2.util.FileUtils;
import com.naruto.didi2.util.LogUtils;
import com.naruto.didi2.util.PermissionUtils;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.functions.Consumer;

public class DbActivity extends AppCompatActivity implements UserUploadListener, View.OnClickListener {

    private Button adduser;
    private Button queryuser;
    private UserDao userDao;
    private Button initdb;
    private Button deleteuser;
    private Button newqueryuser;
    private String[] strings = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private Button upload;
    private int countDown;
    private Button queryuserexclude;
    private Button jsontest;
    private List<String> jsonList;
    private TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);
        initView();
        dbOpen();
        setListener(this);
        PermissionUtils.requestPermissions(this, strings, new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {

            }
        });
        jsonList = new ArrayList<>();
        jsonList.add("1");
        jsonList.add("1");
        jsonList.add("1");
        jsonList.add("1");
        jsonList.add("1");
    }

    private void dbOpen() {
        userDao = new UserDao(this);
    }

    private void initView() {
        adduser = (Button) findViewById(R.id.adduser);
        queryuser = (Button) findViewById(R.id.queryuser);

        adduser.setOnClickListener(this);
        queryuser.setOnClickListener(this);
        initdb = (Button) findViewById(R.id.initdb);
        initdb.setOnClickListener(this);
        deleteuser = (Button) findViewById(R.id.deleteuser);
        deleteuser.setOnClickListener(this);
        newqueryuser = (Button) findViewById(R.id.newqueryuser);
        newqueryuser.setOnClickListener(this);
        upload = (Button) findViewById(R.id.upload);
        upload.setOnClickListener(this);
        queryuserexclude = (Button) findViewById(R.id.queryuserexclude);
        queryuserexclude.setOnClickListener(this);
        jsontest = (Button) findViewById(R.id.jsontest);
        jsontest.setOnClickListener(this);
        content = (TextView) findViewById(R.id.content);
        content.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.adduser:
                //存数据
                for (int i = 0; i < 60; i++) {
                    User user = new User(i + "", "123", "女");
                    userDao.addUser(user);
                }
                break;
            case R.id.queryuser:
                userDao.queryAllUser(true);
                break;
            case R.id.initdb:
//                loadJiaMi();
                copy();
                break;
            case R.id.deleteuser:
                userDao.deleteUser();
//                userDao.updateUserByLimit("15", 0 + "");
                break;
            case R.id.newqueryuser:
                userDao.newQueryAllUser();
                break;
            case R.id.upload:
                uploadData();
                break;
            case R.id.queryuserexclude:
                userDao.queryAllUserExclude();
                break;
            case R.id.jsontest:
//                boolean result = AppUtil.getInstance().checkEmulator(this);
                boolean result = EmulatorCheckUtil.getSingleInstance().checkIsRunningInEmulator(this);
                content.setText("检测模拟器："+result);
                break;
        }
    }

    private void testDate() {
        long time = new Date().getTime();
        long currentTimeMillis = System.currentTimeMillis();
        LogUtils.e("time:" + time + "==" + currentTimeMillis);
    }

    private void testJson2() {
        String json = "{\"app\":{\"pkg\":\"com.tencent.mm\",\"version\":\"8.0.2\"},\"ver\":\"1\",\"content\":{\"permission\":{\"S_SAFE_KEYBOARD\":0,\"S_SMS_SEND\":0,\"S_CALL_LOG_ACCESS\":0,\"S_PRIMARYCLIP_COPY\":20,\"S_SOFT_INPUT\":0,\"S_FRAME_ATTACH\":21,\"S_LOCATION\":0,\"S_PHONE_INFO\":0,\"S_WIFI_SWITCH\":0,\"S_APK_INSTALL\":0,\"S_CHECK_INJECT_STATUS\":0,\"S_CONTACTS_CHANGE\":0,\"S_SHARE_CONTENT\":0,\"S_SMS_CHANGE\":0,\"S_NETWORK_USE_WIFI\":0,\"S_CONTACTS_ACCESS\":0,\"S_WATER_MARK_CFG\":{\"wm_switch\":1,\"view_log\":0,\"alpha\":125,\"view_regex\":\"[^\\\\s]+((id/content|word:id/FullScreenPanesContainer|BindableView|PDFView|wps.moffice_eng:id/content_lay|wps.moffice_eng:id/infoflow_horizonal|PDFFrameLayout|PptRootFrameLayout|RootFrameLayout|MuPDFReaderView|ReaderTextPageView|BookView|WebView|ConstraintLayout|AppCompatEditText|WordCanvasView|ExcelCanvasView|PPTCanvasView))$\",\"fontSize\":60,\"style\":0,\"rgb\":[18,150,219],\"content\":\"上讯信息\",\"bind_time\":0},\"S_APK_UNINSTALL\":0,\"S_SCREENSHOT\":0,\"S_URL\":0,\"S_SMS_ACCESS\":0,\"S_OVERLAY_WINDOW\":0,\"S_CHECK_DEBUG_STATUS\":0,\"S_EMAIL\":0,\"S_SENSOR\":0,\"S_WIFI_WHITE_LIST\":[],\"S_PRIMARYCLIP_PAST\":20,\"S_RECORD_SOUND\":0,\"S_FILE_SECURITY_CFG\":{\"fs_switch\":0},\"S_BLUETOOTH_SWITCH\":0,\"S_CALL_LOG_CHANGE\":0,\"S_CELLULAR_DATA\":0,\"S_MULTIMEDIA\":0,\"S_PRINT\":0,\"S_CAMERA\":0,\"S_WHITE_SO\":[],\"S_CHECK_SIGNATURE\":0,\"S_CHAT_HISTORY\":{\"library\":\"[{\\\"id\\\":5,\\\"name\\\":\\\"少量\\\",\\\"words\\\":\\\"你是猪\\\\n你是狗\\\\n你是驴\\\"}]\",\"replace\":\"true\",\"type\":\"1\",\"collect\":\"true\",\"switch\":21},\"S_CALL\":0,\"S_NOTIFICATION\":0,\"S_NETWORK_USE_MOBILE\":0,\"S_BLUETOOTH_DISCOVER\":0,\"S_SENSITIVE_DATA\":[]}},\"desc\":\"app-permission\"}";
//        String json1 = StringEscapeUtils.unescapeJson(json);
//        String json1 = json.replace("\"","");


        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject content = (JSONObject) jsonObject.get("content");

            //对词库做去\转义符处理
            JSONObject perobj = content.getJSONObject("permission");
            if (perobj.has("S_CHAT_HISTORY")) {
                LogUtils.e("替换library");
                JSONObject s_chat_history = perobj.getJSONObject("S_CHAT_HISTORY");
                String library = s_chat_history.get("library").toString();

                String json1 = StringEscapeUtils.unescapeJson(library);
                s_chat_history.put("library", new JSONArray(json1));
            }
            String permission = content.get("permission").toString();
            permission = permission.replace("\\/", "/");
            LogUtils.e("json1:" + permission);
        } catch (JSONException e) {
            LogUtils.e(e.toString());
        }

    }

    private void testJson1() {
//        JSONArray array = new JSONArray();
//        array.put("检测到root文件");
//        array.put("文件路径：/data/data/root");
//        array.put("判定为设备root");
//
//        LogUtils.e(array.toString());

        String json = "检测到root文件\n文件路径：rootpath\n判定为设备root";
        String[] info = json.split("\n");

        JSONArray array = new JSONArray();
        for (int i = 0; i < info.length; i++) {
            array.put(info[i]);
        }
        LogUtils.e(array.toString());
    }

    private void uploadData() {
        if (userDao == null) {
            return;
        }
        //创建一个子线程
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        List<User> users = userDao.queryAllUser(false);
                        //分割若干小集合
                        List<List<User>> lists = AppUtil.getInstance().groupList(users, 10);
                        countDown += lists.size();
                        for (int i = 0; i < lists.size(); i++) {
                            uploadDataAndUpdate(i * 10, System.currentTimeMillis());
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        ).start();


    }

    /**
     * 上传同时更新表组id
     *
     * @param offset 偏移量
     * @param id     组id
     */
    private void uploadDataAndUpdate(int offset, long id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
//                //根据范围更新组id
                userDao.updateUserByLimit(id + "", offset + "");


                //根据范围更新组id
//                userDao.updateUpLoadStatus(offset+"");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //上传结束，删除数据
                if (offset == 0 || offset == 10 || offset == 30 || offset == 50) {
                    callBack(false, id + "");
                } else {
                    callBack(true, id + "");
                }
            }
        }).start();
    }

    private void copy() {
//        File databasePath = this.getDatabasePath("didi2.db");
//        String databasePath = "data/data/com.mobilewise.mobileware/database/emm.db";
        String databasePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/.emmconfigmanager/files/f953786b77f8c0a5e941bdb356cb14cdfedcef5c3d631c057fad8adc4dd2bebf19.txt";
        String target = Environment.getExternalStorageDirectory().getAbsolutePath() + "/pic/f953786b77f8c0a5e941bdb356cb14cdfedcef5c3d631c057fad8adc4dd2bebf19.txt";
        FileUtils.copyFile(databasePath, target);
    }

    //    private void loadJiaMi() {
//        new Thread(
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            SQLiteDatabase.loadLibs(DbActivity.this);
//                            userDao = new UserDao(DbActivity.this);
//                        } catch (Exception e) {
//                            Log.e(Constants.TAG, "初始化加密数据库异常: " + e.getMessage());
//                        }
//                    }
//                }
//        ).start();
//    }
    public UserUploadListener userUploadListener;

    @Override
    public void result(boolean r, String gid) {
        //更新上传状态
        countDown--;
        if (r) {
//            userDao.deleteUserByGroupId(gid);
            userDao.updateUpLoadStatusByGroupId(gid);
            LogUtils.e("上传成功，更新状态：" + gid);
        } else {
            LogUtils.e("上传失败");
        }
        if (countDown == 0) {
            userDao.deleteUpUser();
            LogUtils.e("上传结束，删除上传成功的记录");
        }
    }

    public void setListener(UserUploadListener userUploadListener) {
        this.userUploadListener = userUploadListener;
    }

    public void callBack(boolean r, String gid) {
        if (userUploadListener != null) {
            userUploadListener.result(r, gid);
        }
    }

    private String getJson(String index) {
        JSONObject object = new JSONObject();
        try {
            object.put("index", index);
            object.put("name", "yang");
            object.put("MD5", "de1713cfa6727f48f86714014bdf943b");
            object.put("actionCode", "010701");

            //增加数组
            String arr = "[\"<2020-12-14 12:37:04> 检测到攻击\",\"<2020-12-14 12:37:04> 判定为注入攻击\"]";
            JSONArray array = new JSONArray(arr);
            object.put("decide", array);

//            LogUtils.e("json:" + object.toString());
            return object.toString();
        } catch (JSONException e) {
            LogUtils.e(e.toString());
        }
        return "--";
    }

    void testJson() {


        List<String> jsonList = new ArrayList<>();
//        List<Behavior> behaviors = new ArrayList<>();

//        String json = getJson("1");
//        Gson gson = new Gson();
//        Behavior behavior1 = gson.fromJson(json, Behavior.class);

//        Behavior behavior = (Behavior) toJsonObject(getJson("1"));
//        Gson gson = new Gson();
//        for (int i = 0; i < 10; i++) {
//            String json = getJson(i + "");
//            Behavior behavior1 = gson.fromJson(json, Behavior.class);
//            behaviors.add(behavior1);
//        }


        jsonList.add(getJson("1"));
        jsonList.add(getJson("2"));
        jsonList.add(getJson("3"));
        jsonList.add(getJson("4"));
        jsonList.add(getJson("5"));
//        LogUtils.e("集合数量：" + jsonList.size());

//        for (int i = 0; i < behaviors.size(); i++) {
//            LogUtils.e("json:" + behaviors.get(i).toString());
//        }


        //遍历json串数组，将每个json转为jsonObject,再将jsonObject放入一个jsonArray
        try {


//            String array = (String) toJsonObject(behaviors);

            //====================================================
            JSONArray array1 = new JSONArray();
            for (int i = 0; i < jsonList.size(); i++) {
                String s = jsonList.get(i);
                JSONObject object = new JSONObject(s);

                array1.put(object);
            }
            LogUtils.e(array1.toString());
        } catch (Exception e) {
            LogUtils.e("Exception: " + e.toString());
        }
        //============================================================

        try {
            JSONArray obArray = new JSONArray(jsonList);
            String s = obArray.toString();
            LogUtils.e("zhijie:" + s);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("格式异常：" + e.toString());
        }

    }

    public Object toJsonObject(Object object) {
        // TODO Auto-generated method stub
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
        String obj = gson.toJson(object);
        return obj;
    }
}
