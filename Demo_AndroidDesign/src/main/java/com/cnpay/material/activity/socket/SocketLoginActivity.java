package com.cnpay.material.activity.socket;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cnpay.material.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 登录客户端页
 * TCP
 *
 * @date: 2018/1/24 0024 上午 10:23
 * @author: yuyucheng
 */
public class SocketLoginActivity extends AppCompatActivity {
    private Button bt_login;
    private EditText edt_name;
    private EditText edt_pass;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket_login);

        findLayout();
    }

    private void findLayout() {
        bt_login = (Button) findViewById(R.id.login_bt_login);
        edt_name = (EditText) findViewById(R.id.login_edt_name);
        edt_pass = (EditText) findViewById(R.id.login_edt_pass);

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkNameAndPass();
            }
        });
    }

    private void checkNameAndPass() {
        final String name = edt_name.getText().toString();
        final String pass = edt_pass.getText().toString();
        if (TextUtils.isEmpty(name)) {
            Log.e("error", "用户名不能为空");
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            Log.e("error", "密码不能为空");
            return;
        }

        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    //步骤①创建Socket对象,指明需要连接的服务器的地址和端口号
                    //host指的是服务器的地址,因为服务器和客户端装在一个手机上,
                    Socket socket = new Socket("localhost", 43211);
                    //步骤②连接建立后,通过输出流向服务器发送请求信息
                    OutputStream os = socket.getOutputStream();
                    PrintWriter pw = new PrintWriter(os);
                    //写入用户名和密码
                    pw.write(name);
                    //用:来分割用户名和密码
                    pw.write(":");
                    pw.write(pass);
                    pw.flush();
                    socket.shutdownOutput();
                     /* //注意!!!!
                        //上面为了简单写用的是字符串,实际开发中用的对象较多,也就是如下写法
                        ObjectOutputStream oos = new ObjectOutputStream(os);
                        User user = new User(name,password);
                        oos.writeObject(user);
                      */


                    //设置超时时间,如果在5s没没有收到服务器的返回,则异常
                    socket.setSoTimeout(1000 * 5);
                    //步骤③通过输入流获取服务器的响应信息
                    InputStream is = socket.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    String info = null;
                    while ((info = br.readLine()) != null) {
                        System.out.println(info);
                    }
                    //步骤④关闭相关资源
                    br.close();
                    is.close();
                    pw.close();
                    os.close();
                    socket.close();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
