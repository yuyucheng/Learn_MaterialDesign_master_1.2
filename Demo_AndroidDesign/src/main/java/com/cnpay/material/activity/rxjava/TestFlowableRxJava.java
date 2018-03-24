package com.cnpay.material.activity.rxjava;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.BufferedReader;
import java.io.FileReader;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * FlowableEmitter.requested()
 * 判断观察者处理能力的使用
 *
 * @date: 2018/2/10 0010 下午 4:09
 * @author: yuyucheng
 */
public class TestFlowableRxJava extends AppCompatActivity{
    private Subscription scrip;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private void baseUser(){
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> e) throws Exception {
                Log.i("start","当前观察者能消费事件数量:"+e.requested());
                //Subscription 不发起消费 默认处理能力 128
                //如果被观察者 和观察者在不同线程 被观察者取默认 e.requested()=128
            }
        }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        scrip=s;
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
                });
    }

    //实践 读取文本 ,每次一句 循环读取
    private void readBook(){
        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> emitter) throws Exception {
                try {
                    FileReader reader=new FileReader("abc.text");
                    BufferedReader bReader=new BufferedReader(reader);
                    String str;

                    while ((str=bReader.readLine())!=null && !emitter.isCancelled()){
                        while (emitter.requested()==0){
                            //如果下游处理能力不为0
                            if (emitter.isCancelled()){
                                break;
                            }
                        }
                        emitter.onNext(str);
                    }

                    bReader.close();
                    reader.close();
                    emitter.onComplete();
                }catch (Exception e){
                    emitter.onError(e);
                }
            }
        },BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        scrip=s;
                        s.request(1);
                    }

                    @Override
                    public void onNext(String s) {
                        Log.i("read",""+s);
                        try {
                            Thread.sleep(2000);
                            scrip.request(1);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
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
