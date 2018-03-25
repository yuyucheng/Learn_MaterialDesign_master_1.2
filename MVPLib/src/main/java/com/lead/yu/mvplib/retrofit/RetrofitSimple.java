package com.lead.yu.mvplib.retrofit;

import com.lead.yu.mvplib.utils.TbLog;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by yuyucheng on 2018/3/25.
 */

public class RetrofitSimple {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://fanyi.youdao.com/") // 设置网络请求的Url地址
            .addConverterFactory(GsonConverterFactory.create()) // 设置数据解析器
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava2平台
            .build();

    // 创建 网络请求接口 的实例
    Request_Interface request = retrofit.create(Request_Interface.class);

    //对 发送请求 进行封装
    Call<RetResponse> call = request.getCall();

    private void baseCall() {
        //发送网络请求(异步)
        call.enqueue(new Callback<RetResponse>() {
            @Override
            public void onResponse(Call<RetResponse> call, Response<RetResponse> response) {

            }

            @Override
            public void onFailure(Call<RetResponse> call, Throwable t) {

            }
        });

        // 发送网络请求（同步）
        try {
            Response<RetResponse> response = call.execute();
            TbLog.i(response.message());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //请求参数相关
    private void useing() {
        // @FormUrlEncoded
        Call<RetResponse> call1 = request.testFormUrlEncoded1("Carson", 24);

        //  @Multipart
//        RequestBody name = RequestBody.create(textType, "Carson");
//        RequestBody age = RequestBody.create(textType, "24");
//
//        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", "test.txt", file);
//        Call<RetResponse> callload1 = request.testFileUpload1(name, age, filePart);

        // @Field // @FieldMap
        Call<RetResponse> callEncoded3 = request.testFormUrlEncoded3("Carson", 24);


        // 实现的效果与上面相同，但要传入Map
        Map<String, Object> map = new HashMap<>();
        map.put("username", "Carson");
        map.put("age", 24);
        Call<RetResponse> callEncoded2 = request.testFormUrlEncoded2(map);

        // @Part & @PartMap
        MediaType textType = MediaType.parse("text/plain");
        RequestBody name = RequestBody.create(textType, "Carson");
        RequestBody age = RequestBody.create(textType, "24");
        RequestBody file = RequestBody.create(MediaType.parse("application/octet-stream"), "这里是模拟文件的内容");

        // @Part
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", "test.txt", file);
        Call<RetResponse> callUpload1 = request.testFileUpload1(name, age, filePart);
        //ResponseBodyPrinter.printResponseBody(callUpload1);

        // @PartMap
        // 实现和上面同样的效果
        Map<String, RequestBody> fileUpload2Args = new HashMap<>();
        fileUpload2Args.put("name", name);
        fileUpload2Args.put("age", age);
        //这里并不会被当成文件，因为没有文件名(包含在Content-Disposition请求头中)，但上面的 filePart 有
        //fileUpload2Args.put("file", file);
        Call<RetResponse> callUpload3= request.testFileUpload3(fileUpload2Args, filePart); //单独处理文件
        //ResponseBodyPrinter.printResponseBody(callUpload3);
    }
}
