package com.cnpay.material.activity.rxjava;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Flowable 解决BlackPressure
 *
 * @date: 2018/2/9 0009 下午 5:24
 * @author: yuyucheng
 */
public class TestBlackPressureRxJava extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //通常用法
    private void useNormal() {
        Flowable<Integer> flowable = Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> e) throws Exception {
                //Flowable里默认有一个大小为128的水缸,最多能存储 128个事件
                e.onNext(1);
            }
        }, BackpressureStrategy.ERROR);//较Observable 增加了一个参数 处理策略
        //BackpressureStrategy.BUFFER 更大的缓存空间
        //BackpressureStrategy.DROP Drop就是直接把存不下的事件丢弃
        //BackpressureStrategy.LATEST Latest就是只保留最新的事件
        Subscriber<Integer> sub = new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                //Subscription 类似 Disposable的流向开关
                s.request(Long.MAX_VALUE);//重要代码

                //s.cancel();//中断传输

                //Subscriber 要调用 request 消费事件 避免 Flowable 溢出
            }

            @Override
            public void onNext(Integer integer) {

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        };

    }


    /**
     * 针对RxJAVA中 非用户自建的Flowable ,无法选择策略问题
     *
     * RxJava中的interval操作符 的使用
     */
    private void dealInter() {
        //interval操作符发送Long型的事件, 从0开始, 每隔指定的时间就把数字加1并发送出来,
        // 在这个例子里, 我们让它每隔2毫秒就发送一次事件, 在下游延时1秒去接收处理
        Flowable.interval(2, TimeUnit.MICROSECONDS)
                .onBackpressureBuffer() //等同BackpressureStrategy.BUFFER
                //.onBackpressureDrop()
                //.onBackpressureLatest()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(125);
                        s.cancel();
                    }

                    @Override
                    public void onNext(Long aLong) {

                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
