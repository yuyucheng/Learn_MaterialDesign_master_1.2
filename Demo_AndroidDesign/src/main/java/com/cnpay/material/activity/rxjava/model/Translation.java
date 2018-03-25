package com.cnpay.material.activity.rxjava.model;

import android.util.Log;

/**
 * 金山翻译
 *
 * @date: 2018/3/7 0007 上午 9:49
 * @author: yuyucheng
 */
public class Translation {
    private int status;

    public content content;
    private static class content {
        private String from;
        private String to;
        private String vendor;
        private String out;
        private int errNo;
    }

    //定义 输出返回数据 的方法
    public void show() {
        Log.d("RxJava", content.out );
    }
}
