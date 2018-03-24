package com.cnpay.material.activity.socket;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.cnpay.material.R;
import com.cnpay.material.thread.ServiceThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Socket 登录服务端
 * TCP
 *
 * @date: 2018/1/24 0024 上午 11:30
 * @author: yuyucheng
 */
public class SocketServiceActivity extends AppCompatActivity {
    private ReceiverThread receiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket_service);

        receiver=new ReceiverThread();
        receiver.start();
    }

    class ReceiverThread extends Thread {

        @Override
        public void run() {
            super.run();

            ServerSocket serverSocket = null;
            Socket socket = null;
            try {
                //步骤①创建ServerSocket对象,绑定监听端口
                //参数port 我们要指定在1024-65535
                //如果端口重复,也就是有其他的应用使用,会报异常
                serverSocket = new ServerSocket(43211);

            } catch (Exception e) {
                e.printStackTrace();
            }

            int count = 0;
            System.out.println("服务器端准备好了链接...");
            //无线循环接受客户端的连接,也就是允许多个客户端连接,当然可以通过ServerSocket的构造方法对连接数做一个限制
            //比如最大连接20,当超过20个连接后会报异常
            while (true) {
                try {
                    //步骤②通过accept()方法监听客户端请求
                    //这个socket是客户端与服务器通信的socket
                    //执行accept方法之后,服务器处于监听状态,随时与客户端连接
                    socket = serverSocket.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ServiceThread serverThread = new ServiceThread(socket);
                //设置优先级为4(默认为5),为了保证程序运行的速度,适当降低该线程的优先级
                serverThread.setPriority(4);
                serverThread.start();

                //记录有多少个连接
                count++;
                System.out.println("当前连接数" + count);
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != receiver) {
            receiver.interrupt();
            receiver = null;
        }
    }
}
