package com.cnpay.material.activity.rxjava;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.cnpay.material.R;
import com.cnpay.material.base.BaseActivity;
import com.cnpay.material.entity.User;
import com.cnpay.material.retrofit.BaseObserver;
import com.cnpay.material.retrofit.BaseResponse;
import com.cnpay.material.retrofit.RetrofitApi;
import com.cnpay.material.retrofit.RetrofitProvider;
import com.cnpay.material.retrofit.RxSchedulers;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Rxjava+retrofit
 * 常规post
 *
 * @date: 2018/3/7 0007 下午 3:11
 * @author: yuyucheng
 */
public class RxNetPostActivity extends BaseActivity{
    TextView tv_base;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_base);
        tv_base=(TextView)findViewById(R.id.rx_tv_base);

        setListener();
    }

    private void setListener(){
        RxView.clicks(tv_base).throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {

                    }
                });
    }

    /**
     * 登录请求
     */
    public void login() {
        //把参数封装到map中
        HashMap map = new HashMap<String, String>();
        map.put("tid", "login");
        map.put("userName", "xiaoming");
        map.put("passWd", "qwer1234");

        //使用类Retrofit生成接口IPost的实现
        RetrofitApi api = RetrofitProvider.getRetrofit().create(RetrofitApi.class);

        api.joinPath(map)
                .compose(RxSchedulers.compose())
                .subscribe(new Observer<String>() {
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
                });

    }

    private void login(String userId, String password) {
        RetrofitApi api=RetrofitProvider.getRetrofit().create(RetrofitApi.class);
        Observable<BaseResponse<User>> observable = api.joinNet(userId, password);
        observable.compose(RxSchedulers.<BaseResponse<User>>compose())
                .subscribe(new BaseObserver<User>(RxNetPostActivity.this) {
                    @Override
                    protected void onHandleSuccess(User user) {
                        // 保存用户信息等操作
                    }
                });
    }
}
