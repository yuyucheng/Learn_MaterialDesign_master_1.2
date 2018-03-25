package com.cnpay.material.activity.socket;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.cnpay.material.R;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Socket UDP 客户端
 *
 * @date: 2018/1/24 0024 下午 2:52
 * @author: yuyucheng
 */
public class SocketPostActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket_post);

        new Thread(){
            @Override
            public void run() {
                //步骤①定义服务器的地址、端口号、数据
                InetAddress address= null;
                try {
                    address = InetAddress.getByName("localhost");
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                int port=8899;
                byte[] data="admin:123".getBytes();
                //步骤②创建数据报，包含发送的数据信息
                DatagramPacket packet=new DatagramPacket(data, data.length, address, port);

                try {
                    //步骤③创建DatagramSocket对象
                    DatagramSocket socket = new DatagramSocket();
                    //步骤④向服务器端发送数据报
                    socket.send(packet);
                    //步骤⑤创建数据报，用于接收服务器端响应的数据
                    byte[] data2=new byte[1024];
                    DatagramPacket packet2=new DatagramPacket(data2, data2.length);
                    //步骤⑥接收服务器响应的数据
                    socket.receive(packet2);
                    //步骤⑦读取数据
                    String reply=new String(data2, 0, packet2.getLength());
                    System.out.println("server reply : "+reply);
                    //步骤⑧关闭资源
                    socket.close();
                } catch (SocketException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
}
