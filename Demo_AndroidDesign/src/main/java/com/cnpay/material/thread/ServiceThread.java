package com.cnpay.material.thread;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Socket服务线程
 *
 * @date: 2018/1/23 0023 下午 4:57
 * @author: yuyucheng
 */
public class ServiceThread extends Thread {
    Socket socket = null;

    public ServiceThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        super.run();

        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        OutputStream os = null;
        PrintWriter pw = null;

        try {
            String info=null;
            StringBuilder strBuilder=new StringBuilder();
            //步骤③建立连接后,通过输入流读取客户端发送的请求信息
            //获取输入流
            is=socket.getInputStream();
            isr=new InputStreamReader(is);
            br=new BufferedReader(isr);
            while ((info=br.readLine())!=null){
                strBuilder.append(info);
            }
            socket.shutdownInput();

            String[] split = strBuilder.toString().split(":");
            String name = split[0];
            String password = split[1];
            //步骤④通过输出流向客户端发送响应信息
            os = socket.getOutputStream();
            pw = new PrintWriter(os);

            //模拟验证账号密码是否正确
            if ("admin".equals(name) && "123456".equals(password)) {
                pw.write("login success!");
            } else {
                pw.write("login failed!!!");
            }
            pw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭相关资源
            try {
                if (null != pw) {
                    pw.close();
                }
                if (null != os)
                    os.close();
                if (null != br)
                    br.close();
                if (null != isr)
                    isr.close();
                if (null != is)
                    is.close();
                if (null != socket)
                    socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
