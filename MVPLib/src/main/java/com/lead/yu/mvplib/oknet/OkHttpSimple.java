package com.lead.yu.mvplib.oknet;


import android.os.Environment;

import com.lead.yu.mvplib.utils.TbLog;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by yuyucheng on 2018/3/25.
 */

public class OkHttpSimple {
    //基础配置
    OkHttpClient.Builder builder = new OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS);
    OkHttpClient okHttpClient = builder.build();

    String url="";


    //基础get
    private void baseGet(){
        Request request = new Request.Builder()
                .method("GET",null)
                .header("key","value")
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response=call.execute();
            TbLog.i("back:"+response.body().string());
        }catch (Exception e){
            e.printStackTrace();
        }

        //response的body有很多种输出方法，string()只是其中之一，注意是string()不是toString()。
        // 如果是下载文件就是response.body().bytes()。
        // 另外可以根据response.code()获取返回的状态码。

    }

    //简单Post
    private void basePost(){
        RequestBody body=new FormBody
                .Builder()
                .add("key","value")
                .build();
        Request request=new Request.Builder()
                .header("Cookie", "xxx")
                .post(body)
                .url(url).build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //post Json
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody jsonbody = RequestBody.create(JSON, "你的json");


        //post文件
        File file=new File("path");
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("image/png"), file))
                .build();


        File vfile = new File(Environment.getExternalStorageDirectory(), "balabala.mp4");

        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), vfile);

        RequestBody vBody = new MultipartBody.Builder()
                .addPart(Headers.of("Content-Disposition",  "form-data; name=\"username\""), RequestBody.create(null, "wgh"))
                .addPart(Headers.of("Content-Disposition",  "form-data; name=\"mFile\"; filename=\"wjd.mp4\""), fileBody)
                .build();

        Request vrequest = new Request.Builder()
                .url("http://192.168.1.103:8080/okHttpServer/fileUpload")
                .post(vBody)
                .build();

        Call vcall = okHttpClient.newCall(vrequest);
        vcall.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });


    }

    //异步get
    private void getAsyTask(){

        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("我是异步线程,线程Id为:" + Thread.currentThread().getId());
            }
        });
        for (int i = 0; i < 10; i++) {
            System.out.println("我是主线程,线程Id为:" + Thread.currentThread().getId());
            try {
                Thread.currentThread().sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
