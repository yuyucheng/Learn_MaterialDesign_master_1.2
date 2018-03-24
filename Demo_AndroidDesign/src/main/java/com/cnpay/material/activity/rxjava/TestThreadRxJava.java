package com.cnpay.material.activity.rxjava;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.cnpay.material.entity.LoginRequest;
import com.cnpay.material.entity.LoginResponse;
import com.cnpay.material.retrofit.RetrofitApi;
import com.cnpay.material.retrofit.RetrofitProvider;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 初步了解RxJava在不同线程中使用
 *
 * @date: 2018/2/8 0008 下午 3:24
 * @author: yuyucheng
 */
public class TestThreadRxJava extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //初步认识 RxJava 分线程
    private void testRxJAVA() {
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {

            }
        });

        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String value) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        //Observable 与 Observer 默认运行与同一个线程中的 我们可以指定其运行线程
        /*  Schedulers.io() 代表io操作的线程, 通常用于网络,读写文件等io密集型的操作
            Schedulers.computation() 代表CPU计算密集型的操作, 例如需要大量计算的操作
            Schedulers.newThread() 代表一个常规的新线程
            AndroidSchedulers.mainThread() 代表Android的主线程
            */
        observable.subscribeOn(Schedulers.newThread())//被观察者所在线程  只有第一次指定有效 ,所在线程 子线程
                .observeOn(AndroidSchedulers.mainThread())//观察者接收端 多次指定均有效 ,所在线程 主线程
                .subscribe(observer);
    }

    /*尝试网络请求使用RxJAVA*/
    private void doRequest() {
        RetrofitApi api = RetrofitProvider.getRetrofit().create(RetrofitApi.class);
        api.login(new LoginRequest())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LoginResponse value) {
                        //返回的封装数据

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("", "登录失败");
                    }

                    @Override
                    public void onComplete() {
                        Log.i("", "登录成功");
                    }
                });
    }
}
