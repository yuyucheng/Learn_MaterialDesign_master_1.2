package com.cnpay.material.retrofit;

import com.cnpay.material.activity.rxjava.model.Translation;
import com.cnpay.material.activity.rxjava.model.Translation2;
import com.cnpay.material.entity.ListCache;
import com.cnpay.material.entity.ListNet;
import com.cnpay.material.entity.ListRequest;
import com.cnpay.material.entity.LoginRequest;
import com.cnpay.material.entity.LoginResponse;
import com.cnpay.material.entity.RegisterRequest;
import com.cnpay.material.entity.RegisterResponse;
import com.cnpay.material.entity.User;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Retrofit 接口
 *
 * @date: 2018/2/8 0008 下午 4:17
 * @author: yuyucheng
 */
public interface RetrofitApi {
// 金山词霸API
// URL模板
//    http://fy.iciba.com/ajax.php

// URL实例
//    http://fy.iciba.com/ajax.php?a=fy&f=auto&t=auto&w=hello%20world

// 参数说明：
// a：固定值 fy
// f：原文内容类型，日语取 ja，中文取 zh，英语取 en，韩语取 ko，德语取 de，西班牙语取 es，法语取 fr，自动则取 auto
// t：译文内容类型，日语取 ja，中文取 zh，英语取 en，韩语取 ko，德语取 de，西班牙语取 es，法语取 fr，自动则取 auto
// w：查询内容

    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20world")
    Observable<Translation> getTranslation();
    // 注解里传入 网络请求 的部分URL地址
    // Retrofit把网络请求的URL分成了两部分：一部分放在Retrofit对象里，另一部分放在网络请求接口里
    // 如果接口里的url是一个完整的网址，那么放在Retrofit对象里的URL可以忽略
    // 采用Observable<...>接口
    // getTranslation()是接受网络请求数据的方法

    // 网络请求2
    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20china")
    Observable<Translation2> getTranslation_2();

    @FormUrlEncoded
    @POST("ajax.mobileSword")
    Observable<String> joinPath(@QueryMap HashMap<String,String> paramsMap);

    @FormUrlEncoded
    @POST("account/login")
    Observable<BaseResponse<User>> joinNet(
            @Field("userId") String userId,
            @Field("password") String password
    );

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @GET
    Observable<LoginResponse> login(@Body LoginRequest request);
    @POST
    Observable<RegisterResponse> register(@Body RegisterRequest request);

    @GET
    Observable<ListNet> getNetList(@Body ListRequest net);

    @GET
    Observable<ListCache> getCacheList(@Body ListRequest cache);
}
