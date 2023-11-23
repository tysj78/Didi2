package com.naruto.didi2.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.naruto.didi2.R;
import com.naruto.didi2.hook.ProxyWaterMark;
import com.naruto.didi2.util.AppUtil;
import com.naruto.didi2.util.LogUtils;
import com.naruto.didi2.util.OkHttpUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class SocketTestActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bt_start_server;
    private EditText et_msg;
    private Button bt_send_data;
    private TextView tv_msg;
    private StringBuffer stringBuffer = new StringBuffer();
    //    private String ipaderss="192.168.206.196";
//    private String ipaderss="192.168.155.1";
//    private String ipaderss="202.99.51.198";
//    private String ipaderss="172.16.2.15";
//    private String ipaderss="127.0.0.1";
    //honor
//    private String ipaderss="10.165.231.237";
    //honor
    private String ipaderss = "192.168.155.4";
    private String ips = "s328m78000.zicp.vip";
//    private String ipaderss = "s328m78000.zicp.vip";
//    private String ipaderss = "103.46.128.53";

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    String message = (String) msg.obj;
                    stringBuffer.append(message).append("\n");
                    tv_msg.setText(stringBuffer.toString());
                    break;
                case 1:
                    et_msg.setText("");
                    break;
                case 2:
                    et_msg.setText("");
                    break;
                case 3:
                    String message3 = (String) msg.obj;
                    stringBuffer.append(message3).append("\n");
                    tv_msg.setText(stringBuffer.toString());
                    break;
                case 4:
                    String message4 = (String) msg.obj;
                    stringBuffer.append(message4).append("\n");
                    tv_msg.setText(stringBuffer.toString());
                    break;
                case 5:
                    String message5 = (String) msg.obj;
                    stringBuffer.append(message5).append("\n");
                    tv_msg.setText(stringBuffer.toString());
                    break;
                default:
                    break;
            }
        }
    };
    private Button bt_ping;
    private Button bt_send_https;
    private Button bt_open_water_mark;
    private Button bu_result;
    private TextView tv_tip;
    private LinearLayout ll_socket_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket_test);
        AppUtil.getInstance().setTransparentForWindow(this);
        initView();
//        openDebug();

        int statusBarHeight = AppUtil.getInstance().getStatusBarHeight(this);
        if (statusBarHeight > 0) {
            LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            layout.setMargins(0, statusBarHeight, 0, 0);
            tv_tip.setLayoutParams(layout);
        }

//        method();
    }

    private void method() {
        int a;
        int b;
        a=new Random(10).nextInt();
        b=new Random(10).nextInt();

        LogUtils.e("a:"+a);
        LogUtils.e("b:"+b);
    }

    private void initView() {

        ll_socket_test = (LinearLayout) findViewById(R.id.ll_socket_test);


        bt_start_server = (Button) findViewById(R.id.bt_start_server);
        et_msg = (EditText) findViewById(R.id.et_msg);
        bt_send_data = (Button) findViewById(R.id.bt_send_data);

        bt_start_server.setOnClickListener(this);
        bt_send_data.setOnClickListener(this);
        tv_msg = (TextView) findViewById(R.id.tv_msg);
        tv_msg.setOnClickListener(this);
        bt_ping = (Button) findViewById(R.id.bt_ping);
        bt_ping.setOnClickListener(this);
        bt_send_https = (Button) findViewById(R.id.bt_send_https);
        bt_send_https.setOnClickListener(this);
        bt_open_water_mark = (Button) findViewById(R.id.bt_open_water_mark);
        bt_open_water_mark.setOnClickListener(this);
        bu_result = (Button) findViewById(R.id.bu_result);
        bu_result.setOnClickListener(this);
        tv_tip = (TextView) findViewById(R.id.tv_tip);
        tv_tip.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_start_server:
                openServer();
                break;
            case R.id.bt_send_data:
                submit();
                break;
            case R.id.bt_ping:
                String string = et_msg.getText().toString();
                ping(string);
                break;
            case R.id.bt_send_https:
                String url = "https://192.168.155.1:8443/didi2.json";
                OkHttpUtil.getInstance().doGet(url, new OkHttpUtil.DataCallBack() {
                    @Override
                    public void onSuccess(String s) {
                        LogUtils.e("获取到网络数据：" + s);
                    }

                    @Override
                    public void onFailure(String f) {
                        LogUtils.e("获取网络数据异常：" + f);
                    }
                });
                break;
            case R.id.bt_open_water_mark:
                ProxyWaterMark.getInstance().hook(getWindow());
                break;
            case R.id.bu_result:
                alert();
                break;
        }
    }

    private void openDebug() {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                boolean ddBug = AppUtil.getInstance().isDdBug();
                LogUtils.e(ddBug ? "应用被调试" : "应用正常运行");
            }
        };
        timer.schedule(timerTask, 0, 30 * 1000);
    }

    private void alert() {
        String n = et_msg.getText().toString();
        if (!n.equals("吴章靖")) {
            Toast.makeText(SocketTestActivity.this, "未获取到测试结果，确认姓名是否正确", Toast.LENGTH_SHORT).show();
            return;
        }
        final AlertDialog builder = new AlertDialog.Builder(this).setTitle("吴章靖的今世老公：")
                .setMessage("杨勇\n\n认可测试结果吗？")
                //  取消选项
                .setNegativeButton("不认可", null)
                //  确认选项
                .setPositiveButton("认可", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //跳转到手机原生设置页面
//                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                        startActivityForResult(intent, GPS_REQUEST_CODE);
                        ProxyWaterMark.getInstance().hook(SocketTestActivity.this.getWindow());
//                        ll_socket_test.setBackgroundResource(R.mipmap.jinger);
                        Toast.makeText(SocketTestActivity.this, "杨勇吴章靖永远在一起ヾ(≧▽≦*)o", Toast.LENGTH_SHORT).show();
                    }
                })
                .setCancelable(false)
//                .show();
                .create();

        builder.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button btnPositive = builder.getButton(DialogInterface.BUTTON_NEGATIVE);
                btnPositive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(SocketTestActivity.this, "请重新确认..", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        builder.show();
    }


    private void ping(final String string) {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        AppUtil.getInstance().isPingSuccess(string, mHandler);
                    }
                }
        ).start();
    }

    private void sendDate(final String msg) {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Socket socket = new Socket(ips, 50882);

                            socket.getOutputStream().write(msg.getBytes());
                            socket.close();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            LogUtils.e(e.toString());
                            Message obtain = Message.obtain();
                            obtain.what = 2;
                            obtain.obj = e.toString();
                            mHandler.sendMessage(obtain);
                        }
                        mHandler.sendEmptyMessage(1);
                    }
                }
        ).start();
    }

    private void openServer() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        byte[] buffer = new byte[1024];
                        try {
                            ServerSocket serverSocket = new ServerSocket(8080);
                            System.out.println("服务器已启动并监听8080端口");
                            while (true) {
                                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                                LogUtils.e("服务器正在等待连接...");
                                Socket socket = serverSocket.accept();
                                LogUtils.e("服务器已接收到连接请求...");
                                LogUtils.e("服务器正在等待数据...");
                                InputStream inputStream = socket.getInputStream();
                                int len = 0;
                                while ((len = inputStream.read(buffer)) != -1) {
                                    byteArrayOutputStream.write(buffer, 0, len);
                                }

                                String msg = byteArrayOutputStream.toString();
//                                socket.getInputStream().read(buffer);
                                LogUtils.e("服务器已经接收到数据");
                                LogUtils.e("接收到的数据:" + msg);
                                Message obtain = Message.obtain();
                                obtain.what = 0;
                                obtain.obj = msg;
                                mHandler.sendMessage(obtain);
                            }
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            LogUtils.e(e.toString());
                        }
                    }
                }
        ).start();
    }

    private void submit() {
        // validate
        String msg = et_msg.getText().toString().trim();
        if (TextUtils.isEmpty(msg)) {
            Toast.makeText(this, "msg不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something
        sendDate(msg);

    }

}
