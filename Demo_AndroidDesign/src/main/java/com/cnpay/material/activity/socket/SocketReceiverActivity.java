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

/**
 * Socket UDP 服务端
 *
 * @date: 2018/1/24 0024 下午 2:52
 * @author: yuyucheng
 */
public class SocketReceiverActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket_receiver);

        new Thread(){
            @Override
            public void run() {
                try {
                    //步骤①创建服务器端DatagramSocket，指定端口

                    DatagramSocket socket = new DatagramSocket(8899);
                    //步骤②创建数据报，用于接收客户端发送的数据
                    byte[] data =new byte[1024];//创建字节数组，指定接收的数据包的大小
                    DatagramPacket packet=new DatagramPacket(data, data.length);
                    //步骤③接收客户端发送的数据
                    System.out.println("server loaded success,waiting...");
                    socket.receive(packet);//此方法在接收到数据报之前会一直阻塞
                    //步骤④读取数据
                    String info=new String(data, 0, packet.getLength());
                    //为了简单起见,这里就不再写验证的逻辑了

                    //步骤⑤定义客户端的地址、端口号、数据
                    InetAddress address=packet.getAddress();
                    int port=packet.getPort();
                    byte[] data2="Welcome!!!".getBytes();
                    //步骤⑥创建数据报，包含响应的数据信息
                    DatagramPacket packet2=new DatagramPacket(data2, data2.length, address, port);
                    //步骤⑦响应客户端
                    socket.send(packet2);
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
