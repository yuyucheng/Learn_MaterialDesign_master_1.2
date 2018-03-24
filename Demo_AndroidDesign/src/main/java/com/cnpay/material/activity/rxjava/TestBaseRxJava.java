package com.cnpay.material.activity.rxjava;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


/**
 * 测试RxJava
 *
 * @date: 2018/2/8 0008 下午 2:44
 * @author: yuyucheng
 */
public class TestBaseRxJava extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /*简洁理想的链式*/
    private void doRx() {
        //RxJava 链式操作
        Observable.create(new ObservableOnSubscribe<Integer>() {
            //被观察者 发射端
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                Log.i("post","---do emitter-1");
                e.onNext(1);
                Log.i("post","---do emitter-2");
                e.onNext(2);
            }
        }).subscribe(new Observer<Integer>() {//Consumer 只关心onNext方法的 Observer
            //观察者 接收端
            @Override
            public void onSubscribe(Disposable d) {
                //d.dispose(); 中断传输
                Log.i("receiver", "控制开关");
            }

            @Override
            public void onNext(Integer value) {
                Log.i("receiver","--收到--"+value);
            }

            @Override
            public void onError(Throwable e) {
                Log.i("receiver","出错了");
            }

            @Override
            public void onComplete() {
                Log.i("receiver","结束了");
            }
        });


        //只关心接收
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {

            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {

            }
        });
    }

    /*典型分开写*/
    private void doRx02(){
        Observable<String> observable=Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("123");
                e.onComplete();
            }
        });

        Observer<String> observer=new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String value) {
                Log.i("receiver","收到"+value);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        observable.subscribe(observer);//发起观察
    }


}
