package com.naruto.didi2.activity;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.naruto.didi2.R;
import com.naruto.didi2.bean.LocationModel;
import com.naruto.didi2.bean.OperationModel;
import com.naruto.didi2.util.LogUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ServerSocketActivity extends AppCompatActivity implements View.OnClickListener {
    public static ServerSocket serverSocket = null;
    public static TextView mTextView, textView1;
    private String IP = "";
    String buffer = "";
    public static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x11) {
                Bundle bundle = msg.getData();
                mTextView.append("client" + bundle.getString("msg") + "\n");
            }
        }
    };
    private HashMap hashMap = new HashMap<>();
    private String channelToken;  //socket 令牌
    private TextView textsss;
    private Button bt_pull;
    private Handler handler;
    private Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_socket);
        initView();
        mTextView = (TextView) findViewById(R.id.textsss);
        textView1 = (TextView) findViewById(R.id.textView1);
        IP = getlocalip();
        //192.168.155.3
        textView1.setText("IP addresss:" + IP);
        new Thread() {
            public void run() {
                try {
                    serverSocket = new ServerSocket(30000);
                } catch (Exception e) {
                    LogUtils.e(e.toString());
                }
                Looper.prepare();
                try {
                    handler = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            try {
                                OutputStream output = socket.getOutputStream();
                                byte[] bytes = objectToByte();
                                output.write(bytes);
                                output.flush();
                                socket.shutdownOutput();

                                output.close();
                                socket.close();
                                LogUtils.e("发送命令成功");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    while (true) {
                        socket = serverSocket.accept();
                    }
//                        BufferedReader bff = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                        channelToken = bff.readLine();
//                        hashMap.put(channelToken, socket);   //保存会话ID和socket


//                        String line = null;
//                        buffer = "";
                            /*while ((line = bff.readLine())!=null) {
                                buffer = line + buffer;
                            }
                            bundle.putString("msg", buffer.toString());
                            msg.setData(bundle);
                            mHandler.sendMessage(msg);*/
                } catch (Exception e) {
                    LogUtils.e("发送命令异常："+e.toString());
                }
                Looper.loop();
            }

        }.start();
    }

    /**
     * 或取本机的ip地址
     */
    private String getlocalip() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        //  Log.d(Tag, "int ip "+ipAddress);
        if (ipAddress == 0) return null;
        return ((ipAddress & 0xff) + "." + (ipAddress >> 8 & 0xff) + "."
                + (ipAddress >> 16 & 0xff) + "." + (ipAddress >> 24 & 0xff));
    }

    public byte[] objectToByte() {
        OperationModel operationModel = new OperationModel();
        LocationModel locationModel1 = new LocationModel();
        locationModel1.setX(600);
        locationModel1.setY(0);
        LocationModel locationModel2 = new LocationModel();
        locationModel2.setX(600);
        locationModel2.setY(2000);
        List<LocationModel> lis = new ArrayList<>();
        lis.add(locationModel1);
        lis.add(locationModel2);
        operationModel.setList(lis);
        operationModel.setDurationTime(2000);
        byte[] bytes = null;
        try {
            // object to bytearray
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(operationModel);

            bytes = bo.toByteArray();

            bo.close();
            oo.close();
            LogUtils.e("转换字节数组成功--");
        } catch (Exception e) {
            System.out.println("转换字节数组异常：" + e.getMessage());
            LogUtils.e(e.toString());
            e.printStackTrace();
        }
        return bytes;
    }

    private void initView() {
        textsss = (TextView) findViewById(R.id.textsss);
        bt_pull = (Button) findViewById(R.id.bt_pull);

        bt_pull.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_pull:
                handler.sendEmptyMessage(1);
                break;
        }
    }
}
