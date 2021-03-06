package com.yangyong.guard;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mobilewise.guard.R;
import com.yangyong.guard.bean.OperationModel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ClientActivity extends AppCompatActivity {
    private static final String TAG = "yy";
    Socket socket = null;
    String buffer = "";
    TextView txt1;
    Button send;
    EditText ed1;
    String geted1;
    public Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x11) {
                Bundle bundle = msg.getData();
                txt1.append("server:" + bundle.getString("msg") + "\n");
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        txt1 = (TextView) findViewById(R.id.txt1);
        send = (Button) findViewById(R.id.send);
        ed1 = (EditText) findViewById(R.id.ed1);
        send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                geted1 = ed1.getText().toString();
//                txt1.append("client:" + geted1 + "\n");
                //启动线程 向服务器发送和接收信息
                new MyThread(geted1).start();
            }
        });
    }

    class MyThread extends Thread {

        public String txt1;

        public MyThread(String str) {
            txt1 = str;
        }

        @Override
        public void run() {
            //定义消息
            Message msg = new Message();
            msg.what = 0x11;
            Bundle bundle = new Bundle();
            bundle.clear();
            try {
                //连接服务器 并设置连接超时为1秒
                socket = new Socket();
                socket.connect(new InetSocketAddress("192.168.155.3", 30000), 1000); //端口号为30000
                //获取输入输出流
                OutputStream ou = socket.getOutputStream();
//                BufferedReader bff = new BufferedReader(new InputStreamReader(
//                        socket.getInputStream()));
                InputStream inputStream = socket.getInputStream();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                //读取发来服务器信息
                int line = 0;
                buffer = "";
                byte bytes[]=new byte[1024];
                while ((line = inputStream.read(bytes)) != -1) {
//                    buffer = line + buffer;
                    outputStream.write(bytes,0,line);

                }
                byte[] bytes1 = outputStream.toByteArray();
                OperationModel model = (OperationModel)byteToObject(bytes1);

                AppUtils.sendMsg(ClientActivity.this,model);


                /*//向服务器发送信息
                ou.write(txt1.getBytes("utf-8"));
                ou.flush();
                outputStream.close();
                bundle.putString("msg", buffer.toString());
                msg.setData(bundle);
                //发送消息 修改UI线程中的组件
                myHandler.sendMessage(msg);*/
                //关闭各种输入输出流
                outputStream.close();
                ou.close();
                socket.close();
            } catch (SocketTimeoutException aa) {
                //连接超时 在UI界面显示消息
                bundle.putString("msg", "服务器连接失败！请检查网络是否打开");
                msg.setData(bundle);
                //发送消息 修改UI线程中的组件
                myHandler.sendMessage(msg);
            } catch (IOException e) {
                Log.e(TAG, "接收手势命令异常： "+e.toString() );
//                e.printStackTrace();
            }
        }
    }

    public Object byteToObject(byte[] bytes) {
        Object obj = null;
        try {
            // bytearray to object
            ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
            ObjectInputStream oi = new ObjectInputStream(bi);

            obj = oi.readObject();
            bi.close();
            oi.close();
            Log.e(TAG, "转化对象成功");
        } catch (Exception e) {
            Log.e(TAG, "转化对象异常：" + e.getMessage());
//            e.printStackTrace();
        }
        return obj;
    }

}
