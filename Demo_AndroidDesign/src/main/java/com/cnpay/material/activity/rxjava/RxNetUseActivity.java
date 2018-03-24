package com.cnpay.material.activity.rxjava;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.cnpay.material.R;
import com.cnpay.material.activity.rxjava.model.Translation;
import com.cnpay.material.base.BaseActivity;
import com.cnpay.material.entity.LoginRequest;
import com.cnpay.material.entity.LoginResponse;
import com.cnpay.material.entity.RegisterRequest;
import com.cnpay.material.entity.RegisterResponse;
import com.cnpay.material.retrofit.RetrofitApi;
import com.cnpay.material.retrofit.RetrofitProvider;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Rx网络使用相关
 *
 * @date: 2018/3/7 0007 上午 9:38
 * @author: yuyucheng
 */
public class RxNetUseActivity extends BaseActivity {
    private static final String TAG = "RxJava";
    private  RetrofitApi api;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_base);

        // 步骤1：创建Retrofit对象
        // 步骤2：创建 网络请求接口 的实例
        RetrofitApi api = RetrofitProvider.getRetrofit().create(RetrofitApi.class);
    }

    /**************************
     *  网络轮询
     * ***********************************/
    private void netLunXun() {
        /*
         * 步骤1：采用interval（）延迟发送
         * 注：此处主要展示无限次轮询，若要实现有限次轮询，仅需将interval（）改成intervalRange（）即可
         *
         * // 参数1 = 第1次延迟时间；
         * // 参数2 = 间隔时间数字；
         * // 参数3 = 时间单位；
         **/
        Observable.interval(2, 1, TimeUnit.SECONDS).doOnNext(new Consumer<Long>() {
            @Override
            public void accept(Long integer) throws Exception {
                Log.d("NET", "第 " + integer + " 次轮询");

                // c. 采用Observable<...>形式 对 网络请求 进行封装
                Observable<Translation> observable = api.getTranslation();
                // d. 通过线程切换发送网络请求
                observable.subscribeOn(Schedulers.io())             // 切换到IO线程进行网络请求
                        .observeOn(AndroidSchedulers.mainThread())  // 切换回到主线程 处理请求结果
                        .subscribe(new Observer<Translation>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(Translation result) {
                                // e.接收服务器返回的数据
                                result.show();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d("NET", "请求失败");
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        }).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long value) {

            }

            @Override
            public void onError(Throwable e) {
                Log.d("NET", "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d("NET", "对Complete事件作出响应");
            }
        });
    }

    /*************************************************************************/


    // 设置变量
    // 可重试次数
    private int maxConnectCount = 10;
    // 当前已重试次数
    private int currentRetryCount = 0;
    // 重试等待时间
    private int waitRetryTime = 0;

    /*****************************
     *  网络请求出错重连需求
     * ****************************************/
    private void tryError() {
        // 步骤3：采用Observable<...>形式 对 网络请求 进行封装
        Observable<Translation> observable = api.getTranslation();

        // 步骤4：发送网络请求 & 通过retryWhen（）进行重试
        // 注：主要异常才会回调retryWhen（）进行重试
        observable.retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {
                // 参数Observable<Throwable>中的泛型 = 上游操作符抛出的异常，可通过该条件来判断异常的类型
                return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull Throwable throwable) throws Exception {
                        // 输出异常信息
                        Log.d(TAG, "发生异常 = " + throwable.toString());

                        /**
                         * 需求1：根据异常类型选择是否重试
                         * 即，当发生的异常 = 网络异常 = IO异常 才选择重试
                         */
                        if (throwable instanceof IOException) {
                            Log.d(TAG, "属于IO异常，需重试");

                            /**
                             * 需求2：限制重试次数
                             * 即，当已重试次数 < 设置的重试次数，才选择重试
                             */
                            if (currentRetryCount < maxConnectCount) {
                                // 记录重试次数
                                currentRetryCount++;
                                Log.d(TAG, "重试次数 = " + currentRetryCount);

                                /**
                                 * 需求2：实现重试
                                 * 通过返回的Observable发送的事件 = Next事件，从而使得retryWhen（）重订阅，最终实现重试功能
                                 *
                                 * 需求3：延迟1段时间再重试
                                 * 采用delay操作符 = 延迟一段时间发送，以实现重试间隔设置
                                 *
                                 * 需求4：遇到的异常越多，时间越长
                                 * 在delay操作符的等待时间内设置 = 每重试1次，增多延迟重试时间1s
                                 */
                                // 设置等待时间
                                waitRetryTime = 1000 + currentRetryCount * 1000;
                                Log.d(TAG, "等待时间 =" + waitRetryTime);
                                return Observable.just(1).delay(waitRetryTime, TimeUnit.MILLISECONDS);


                            } else {
                                // 若重试次数已 > 设置重试次数，则不重试
                                // 通过发送error来停止重试（可在观察者的onError（）中获取信息）
                                return Observable.error(new Throwable("重试次数已超过设置次数 = " + currentRetryCount + "，即 不再重试"));

                            }
                        } else {
                            // 若发生的异常不属于I/O异常，则不重试
                            // 通过返回的Observable发送的事件 = Error事件 实现（可在观察者的onError（）中获取信息）
                            return Observable.error(new Throwable("发生了非网络异常（非I/O异常）"));
                        }
                    }
                });
            }
        }).subscribeOn(Schedulers.io())               // 切换到IO线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread())  // 切换回到主线程 处理请求结果
                .subscribe(new Observer<Translation>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Translation result) {
                        // 接收服务器返回的数据
                        Log.d(TAG, "发送成功");
                        result.show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        // 获取停止重试的信息
                        Log.d(TAG, e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    /*****************************************************************************/

    /*******************************
     *  网络请求嵌套回调
     *
     *  需要进行嵌套网络请求：即在第1个网络请求成功后，继续再进行一次网络请求
     *  如 先进行 用户注册 的网络请求, 待注册成功后回再继续发送 用户登录 的网络请求
     * ************************************/

    /*********通常用法************/
    // 发送注册网络请求的函数方法
    private void register() {
        api.register(new RegisterRequest())
                .subscribeOn(Schedulers.io())               //在IO线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread())  //回到主线程去处理请求结果
                .subscribe(new Consumer<RegisterResponse>() {
                    @Override
                    public void accept(RegisterResponse registerResponse) throws Exception {
                        Toast.makeText(RxNetUseActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        login();   //注册成功, 调用登录的方法
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(RxNetUseActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    // 发送登录网络请求的函数方法
    private void login() {
        api.login(new LoginRequest())
                .subscribeOn(Schedulers.io())               //在IO线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread())  //回到主线程去处理请求结果
                .subscribe(new Consumer<LoginResponse>() {
                    @Override
                    public void accept(LoginResponse loginResponse) throws Exception {
                        Toast.makeText(RxNetUseActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(RxNetUseActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /********操作符用法**********/
    private void toLogin(){
        final RetrofitApi api= RetrofitProvider.getRetrofit().create(RetrofitApi.class);
        api.register(new RegisterRequest())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<RegisterResponse>() {
                    @Override
                    public void accept(RegisterResponse registerResponse) throws Exception {
                        //注册结果逻辑
                    }
                })
                .observeOn(Schedulers.io())//重设接收逻辑 回到IO线程去发起登录请求
                .flatMap(new Function<RegisterResponse, ObservableSource<LoginResponse>>() {
                    @Override
                    public ObservableSource<LoginResponse> apply(RegisterResponse registerResponse) throws Exception {
                        return api.login(new LoginRequest());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())// //回到主线程去处理请求登录的结果
                .subscribe(new Consumer<LoginResponse>() {
                    @Override
                    public void accept(LoginResponse loginResponse) throws Exception {
                        Log.i("","登录成功");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i("","登录失败");
                    }
                });

    }
}
