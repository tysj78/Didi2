package com.naruto.didi2.activity.third;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.naruto.didi2.R;
import com.naruto.didi2.bean.Person;
import com.naruto.didi2.broadcast.LocalReceiver;
import com.naruto.didi2.broadcast.TestReceiver;
import com.naruto.didi2.util.AppUtil;
import com.naruto.didi2.util.LogUtils;
import com.naruto.didi2.util.ThreadPoolUtil;
import com.naruto.didi2.util.WorkThread;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class TestDemo extends AppCompatActivity implements View.OnClickListener {

    private Button mWriteObjectBt;
    private Button mReadFileBt;
    private Button mListdelrepeatBt;
    private Button mGetwifiipBt;
    private TextView mContentTv;
    private Button mRegtest1Bt;
    private Button mSendtest1Bt;
    //    private final String LOCALACTION = "com.didi2.local";
    private final String LOCALACTION = "didi2.test";
    private ImageView mPicIv;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_demo);
        initView();

//        TestReceiver testReceiver = new TestReceiver();
//        IntentFilter intentFilter = new IntentFilter("didi2.test");
//        registerReceiver(testReceiver, intentFilter);
//        LogUtils.e("注册test1广播完成");
//        initLocalRec();

//        PermissionReceiver permissionReceiver = new PermissionReceiver();
//        IntentFilter intentFilter = new IntentFilter("naruto.intent.permission");
//        registerReceiver(permissionReceiver,intentFilter,"didi2.permission",null);
//
//        LogUtils.e("注册跨进程动态广播");

        boolean b = AppUtil.getInstance().hasEnable(this);
        mContentTv.setText("设备使用情况权限：" + (b ? "开启" : "关闭"));
    }

    private void initView() {
        mWriteObjectBt = (Button) findViewById(R.id.bt_writeObject);
        mWriteObjectBt.setOnClickListener(this);
        mReadFileBt = (Button) findViewById(R.id.bt_readFile);
        mReadFileBt.setOnClickListener(this);
        mListdelrepeatBt = (Button) findViewById(R.id.bt_listdelrepeat);
        mListdelrepeatBt.setOnClickListener(this);
        mGetwifiipBt = (Button) findViewById(R.id.bt_getwifiip);
        mGetwifiipBt.setOnClickListener(this);
        mContentTv = (TextView) findViewById(R.id.tv_content);
        mRegtest1Bt = (Button) findViewById(R.id.bt_regtest1);
        mRegtest1Bt.setOnClickListener(this);
        mSendtest1Bt = (Button) findViewById(R.id.bt_sendtest1);
        mSendtest1Bt.setOnClickListener(this);
        mPicIv = (ImageView) findViewById(R.id.iv_pic);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_writeObject:
                // TODO 23/03/09
                ThreadPoolUtil.getInstance().start(new WorkThread() {
                    @Override
                    public void runInner() {
                        write();
                    }
                });
                break;
            case R.id.bt_readFile:
                // TODO 23/03/09
                ThreadPoolUtil.getInstance().start(new WorkThread() {
                    @Override
                    public void runInner() {
                        read();
                    }
                });
                break;
            case R.id.bt_listdelrepeat:// TODO 23/03/29
                delrepeat();
                break;
            case R.id.bt_getwifiip:// TODO 23/04/03
                break;
            case R.id.bt_regtest1:// TODO 23/04/06

                break;
            case R.id.bt_sendtest1:// TODO 23/04/06
                //发隐式广播
//                Intent intent = new Intent("didi2.test");
//                intent.putExtra("jd","turtjtyjtyjtyjk");
//                sendBroadcast(intent);
                //发送显式广播,指定组件名称
                Intent intent = new Intent();
                intent.putExtra("jd", "turtjtyjtyjtyjk");
                intent.setComponent(new ComponentName(this, TestReceiver.class));
                sendBroadcast(intent);

                //发送本地广播
//                Intent intent = new Intent();
//                intent.setAction(LOCALACTION);
//                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 注册本地广播
     */
    private void initLocalRec() {
        LocalReceiver localReceiver = new LocalReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(localReceiver, new IntentFilter(LOCALACTION));
    }

    private void delrepeat() {
        try {
            ArrayList<String> list = new ArrayList<>();
            list.add("178");
            list.add("179");
            list.add("178");
            list.add("133");
            list.add("156");
            list.add("200");
            list.add("156");

            HashSet<String> set = new HashSet<>(list);

            List<String> list1 = new ArrayList<>(set);

            String newlist1 = Arrays.toString(list1.toArray());
            LogUtils.e(newlist1);
        } catch (Exception e) {
            LogUtils.exception(e);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
        } catch (Exception e) {
            LogUtils.exception(e);
        }
    }

    private void read() {
        String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(absolutePath + File.separator + "didiout.txt");
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(fis);
                try {
                    Person person = (Person) ois.readObject();   //读出对象
                    LogUtils.e(person.toString());
                } catch (ClassNotFoundException e) {
                    LogUtils.e(e.toString());
                }
            } catch (IOException e) {
                LogUtils.e(e.toString());
            } finally {
                try {
                    ois.close();
                } catch (IOException e) {
                    LogUtils.e("ois关闭失败：" + e.toString());
                }
            }
        } catch (FileNotFoundException e) {
            LogUtils.e("找不到文件：" + e.toString());
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                LogUtils.e("fis关闭失败：" + e.toString());
            }
        }
    }

    private void write() {
        String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(absolutePath + File.separator + "didiout.txt");

        FileOutputStream fos = null;
        try {
            LogUtils.e("开始写入对象");
            fos = new FileOutputStream(file);
            ObjectOutputStream oos = null;
            try {
                oos = new ObjectOutputStream(fos);
                Person person = new Person("tom", 22);
                System.out.println(person);
                oos.writeObject(person);            //写入对象
                oos.flush();

                LogUtils.e("写入对象到文件完成");
            } catch (IOException e) {
                LogUtils.e("写入异常：" + e.toString());
            } finally {
                try {
                    oos.close();
                } catch (IOException e) {
                    LogUtils.e("oos关闭失败：" + e.toString());
                }
            }
        } catch (FileNotFoundException e) {
            LogUtils.e("找不到文件：" + e.toString());
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                LogUtils.e("fos关闭失败：" + e.toString());
            }
        }
    }


}
