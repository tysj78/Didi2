package com.yangyong.guard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSocketActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_content;
    private Button bt_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_socket);
        initView();

    }

    private void initView() {
        et_content = (EditText) findViewById(R.id.et_content);
        bt_send = (Button) findViewById(R.id.bt_send);

        bt_send.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_send:
                String content = et_content.getText().toString();
                sendMsg(content);
                break;
        }
    }

    private void sendMsg(final String content) {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // 和服务器创建连接
                            Socket socket = new Socket("192.168.155.2", 8088);
//                            Socket socket = new Socket("117.136.38.152", 8088);



                            // 要发送给服务器的信息
                            OutputStream os = socket.getOutputStream();
                            PrintWriter pw = new PrintWriter(os);
                            pw.write(content);
                            pw.flush();

                            socket.shutdownOutput();

                            // 从服务器接收的信息
                            InputStream is = socket.getInputStream();
                            BufferedReader br = new BufferedReader(new InputStreamReader(is));
                            String info = null;
                            while ((info = br.readLine()) != null) {
                                Log.e("yy", "我是客户端，服务器返回信息：" + info);
                            }
                            br.close();
                            is.close();
                            os.close();
                            pw.close();
                            socket.close();
                        } catch (Exception e) {
                            Log.e("yy", "sendMsg: " + e.toString());
                        }
                    }
                }
        ).start();
    }
}
