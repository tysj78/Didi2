package com.naruto.didi2.thread;

import android.os.Handler;
import android.os.Message;

import com.naruto.didi2.util.LogUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * xxx class
 *
 * @author yangyong
 * @date 2020/7/14/0014
 */

public class ServerThread extends Thread {
    private Handler handler;
    private StringBuilder stringBuilder;
    public ServerThread(Handler handler) {
        this.handler = handler;
        stringBuilder=new StringBuilder();
    }

    @Override
    public void run() {
        try {
            LogUtils.e("初始化服务");
            // 创建服务端socket
            ServerSocket serverSocket = new ServerSocket(8088);

            // 创建客户端socket
            Socket socket = null;

            //循环监听等待客户端的连接
            while (true) {
                // 监听客户端
                socket = serverSocket.accept();

                InetAddress address = socket.getInetAddress();
                LogUtils.e("当前客户端的IP：" + address.getHostAddress());


                InputStream is = null;
                InputStreamReader isr = null;
                BufferedReader br = null;
                OutputStream os = null;
                PrintWriter pw = null;
                try {
                    is = socket.getInputStream();
                    isr = new InputStreamReader(is);
                    br = new BufferedReader(isr);

                    String info = null;
                    while ((info = br.readLine()) != null) {
                        LogUtils.e("我是服务器，客户端说：" + info);
                        stringBuilder.append("接收到客户端消息："+info+"\r\n");
                    }
                    Message obtain = Message.obtain();
                    obtain.what=1;
                    obtain.obj=stringBuilder.toString();
                    handler.sendMessage(obtain);

                    socket.shutdownInput();

                    os = socket.getOutputStream();
                    pw = new PrintWriter(os);
                    pw.write("服务器欢迎你");

                    pw.flush();
                } catch (Exception e) {
                    LogUtils.e(e.toString());
                } finally {
                    //关闭资源
                    try {
                        if (pw != null)
                            pw.close();
                        if (os != null)
                            os.close();
                        if (br != null)
                            br.close();
                        if (isr != null)
                            isr.close();
                        if (is != null)
                            is.close();
                        if (socket != null)
                            socket.close();
                    } catch (IOException e) {
                        LogUtils.e(e.toString());
                    }
                }
            }
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }
    }
}
