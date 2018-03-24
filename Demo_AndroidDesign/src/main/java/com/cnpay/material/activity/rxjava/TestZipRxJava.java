package com.cnpay.material.activity.rxjava;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.cnpay.material.entity.ListCache;
import com.cnpay.material.entity.ListNet;
import com.cnpay.material.entity.ListRequest;
import com.cnpay.material.entity.User;
import com.cnpay.material.retrofit.RetrofitApi;
import com.cnpay.material.retrofit.RetrofitProvider;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;


/**
 * RxJava zip 组合
 *
 * @date: 2018/2/9 0009 上午 10:08
 * @author: yuyucheng
 */
public class TestZipRxJava extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Zip通过一个函数将多个Observable发送的事件结合到一起，然后发送这些组合到一起的事件.
        // 它按照严格的顺序应用这个函数。它只发射与发射数据项最少的那个Observable一样多的数据。
    }

    private void testZip() {
        Observable<String> observable1 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("1");
                e.onNext("2");
                e.onNext("3");
                e.onNext("4");
                e.onNext("5");
                e.onComplete();
            }
        });
        Observable<Integer> observable2 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
            }
        });

        Observable.zip(observable1, observable2, new BiFunction<String, Integer, String>() {

            @Override
            public String apply(String s, Integer integer) throws Exception {
                //将想要整合的类型返回出去
                return s + integer;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String value) {
                Log.i("observer", "观察到结果:" + value);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /*实践应用 获取不同的资源 合成想要的数据*/
    private void doRequest() {
        RetrofitApi api = RetrofitProvider.getRetrofit().create(RetrofitApi.class);
        Observable<ListNet> observable1 = api.getNetList(new ListRequest())
                .subscribeOn(Schedulers.io());
        Observable<ListCache> observable2 = api.getCacheList(new ListRequest())
                .subscribeOn(Schedulers.io());

        Observable.zip(observable1, observable2, new BiFunction<ListNet, ListCache, User>() {
            @Override
            public User apply(ListNet net, ListCache cache) throws Exception {
                return new User(net.getData()+cache.getData());
            }
        }).observeOn(AndroidSchedulers.mainThread())//回到主线程显示
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(User value) {
                        Log.i("收到", "合成:" + value.getData());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
