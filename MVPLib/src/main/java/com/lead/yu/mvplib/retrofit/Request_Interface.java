package com.lead.yu.mvplib.retrofit;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by yuyucheng on 2018/3/25.
 */

public interface Request_Interface {
    //网络请求的完整 Url =在创建Retrofit实例时通过.baseUrl()设置 +网络请求接口的注解设置（下面称 “path“ ）
    //url=baseUrl+path

    @GET("openapi.do?keyfrom=Yanzhikai&key=2032414398&type=data&doctype=json&version=1.1&q=car")
    Call<RetResponse> getCall();
    // @GET注解的作用:采用Get方法发送网络请求
    // getCall() = 接收网络请求数据的方法
    // 其中返回类型为Call<*>，*是接收数据的类（即上面定义的RetResponse类）


    //请求方法: @GET、@POST、@PUT、@DELETE、@HEAD  @HTTP(http 用于对前面的方法拓展)
    //标记类:
    // @FormUrlEncoded 作用：表示发送form-encoded的数据;
    // @Multipart 作用：表示发送form-encoded的数据（适用于 有文件 上传的场景）
    //网络请求参数
    //@Header & @Headers 作用：添加请求头 &添加不固定的请求头
    //@Body              作用：以 Post方式 传递 自定义数据类型 给服务器,如果提交的是一个Map，那么作用相当于 @Field
    //@Field & @FieldMap 作用：发送 Post请求 时提交请求的表单字段 与 @FormUrlEncoded 注解配合使用
    //@Part & @PartMap   作用：发送 Post请求 时提交请求的表单字段,
    // 与@Field的区别：功能相同，但携带的参数类型更加丰富，包括数据流，所以适用于 有文件上传 的场景

    //@Query和@QueryMap   作用：用于 @GET 方法的查询参数（Query = Url 中 ‘?’ 后面的 key-value）
    //如：url = http://www.println.net/?cate=android，其中，Query = cate
    //@Path              作用：URL地址的缺省值
    //@Url              作用：直接传入一个请求的 URL变量 用于URL设置

    /**********************************************************************/

    /**
     * method：网络请求的方法（区分大小写）
     * path：网络请求地址路径
     * hasBody：是否有请求体
     */
    @HTTP(method = "GET", path = "blog/{id}", hasBody = false)
    Call<RetResponse> getCall(@Path("id") int id);
    // {id} 表示是一个变量
    // method 的值 retrofit 不会做处理，所以要自行保证准确

    //标记类

    /**
     * FormUrlEncoded
     * <p>
     * 表明是一个表单格式的请求（Content-Type:application/x-www-form-urlencoded）
     * <code>Field("username")</code> 表示将后面的 <code>String name</code> 中name的取值作为 username 的值
     */
    @POST("/form")
    @FormUrlEncoded
    Call<RetResponse> testFormUrlEncoded1(@Field("username") String name, @Field("age") int age);

    /**
     * Multipart 适用于 有文件 上传的场景
     * <p>
     * {@link Part} 后面支持三种类型，{@link RequestBody}、{@link okhttp3.MultipartBody.Part} 、任意类型
     * 除 {@link okhttp3.MultipartBody.Part} 以外，其它类型都必须带上表单字段({@link okhttp3.MultipartBody.Part} 中已经包含了表单字段的信息)，
     */
    @POST("/form")
    @Multipart
    Call<RetResponse> testFileUpload1(@Part("name") RequestBody name, @Part("age") RequestBody age, @Part MultipartBody.Part file);


    //网络请求参数

    // @Header
    @GET("user")
    Call<RetResponse> getUser(@Header("Authorization") String authorization);

    // @Headers
    @Headers("Authorization: authorization")
    @GET("user")
    Call<RetResponse> getUser();

    // 以上的效果是一致的。
    // 区别在于使用场景和使用方式
    // 1. 使用场景：@Header用于添加不固定的请求头，@Headers用于添加固定的请求头
    // 2. 使用方式：@Header作用于方法的参数；@Headers作用于方法

    //@Body
    @POST("/request")
    Call<RetResponse> loadRequest(@Body RetRequest request);
    //如果提交的是一个Map，那么作用相当于 @Field



    //@Field
    /**
     *表明是一个表单格式的请求（Content-Type:application/x-www-form-urlencoded）
     * <code>Field("username")</code> 表示将后面的 <code>String name</code> 中name的取值作为 username 的值
     */
    @POST("/form")
    @FormUrlEncoded
    Call<RetResponse> testFormUrlEncoded3(@Field("username") String name, @Field("age") int age);

    //@FieldMap
    /**
     * Map的key作为表单的键
     */
    @POST("/form")
    @FormUrlEncoded
    Call<RetResponse> testFormUrlEncoded2(@FieldMap Map<String, Object> map);



    //@Part 与 @Multipart 注解配合使用
    /**
     * {@link Part} 后面支持三种类型，{@link RequestBody}、{@link okhttp3.MultipartBody.Part} 、任意类型
     * 除 {@link okhttp3.MultipartBody.Part} 以外，其它类型都必须带上表单字段({@link okhttp3.MultipartBody.Part} 中已经包含了表单字段的信息)，
     */
    @POST("/form")
    @Multipart
    Call<RetResponse> testFileUpload2(@Part("name") RequestBody name, @Part("age") RequestBody age, @Part MultipartBody.Part file);

    //@PartMap 与 @Multipart 注解配合使用
    /**
     * PartMap 注解支持一个Map作为参数，支持 {@link RequestBody } 类型，
     * 如果有其它的类型，会被{@link retrofit2.Converter}转换，如后面会介绍的 使用{@link com.google.gson.Gson} 的 {@link retrofit2.converter.gson.GsonRequestBodyConverter}
     * 所以{@link MultipartBody.Part} 就不适用了,所以文件只能用<b> @Part MultipartBody.Part </b>
     */
    @POST("/test")
    @Multipart
    Call<RetResponse> testFileUpload3(@PartMap Map<String, RequestBody> args, @Part MultipartBody.Part file);

    @POST("/upload")
    @Multipart
    Call<RetResponse> testFileUpload4(@PartMap Map<String, RequestBody> args);



    //@Query和@QueryMap
    // 用于 @GET 方法的查询参数（Query = Url 中 ‘?’ 后面的 key-value）
    //如：url = http://www.println.net/?cate=android，其中，Query = cate
    @GET("/")
    Call<RetResponse> cate(@Query("cate") String cate);
    // 其使用方式同 @Field与@FieldMap

    //@Path
    @GET("users/{user}/repos")
    Call<RetResponse>  getBlog(@Path("user") String user );
    // 访问的API是：https://api.github.com/users/{user}/repos
    // 在发起请求时， {user} 会被替换为方法的第一个参数 user（被@Path注解作用）

    @GET
    Call<RetResponse> testUrlAndQuery(@Url String url, @Query("showAll") boolean showAll);
    // 当有URL注解时，@GET传入的URL就可以省略
    // 当GET、POST...HTTP等方法中没有设置Url时，则必须使用 {@link Url}提供
}
