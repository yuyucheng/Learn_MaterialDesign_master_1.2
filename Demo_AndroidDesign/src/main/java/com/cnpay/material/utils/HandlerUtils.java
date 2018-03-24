package com.cnpay.material.utils;

import android.os.Handler;
import android.os.Looper;

/**
 * 包   名:     com.cnpay.material.utils
 * 类   名:     HandlerUtils
 * 版权所有:     版权所有(C)2010-2016
 * 公   司:     深圳华夏通宝信息技术有限公司
 * 版   本:          V1.0
 * 时   间:     2016/12/1 0001 15:52
 * 作   者:     yuyucheng
 */
public class HandlerUtils {
    private HandlerUtils() {}

    private static final Handler handler = new Handler(Looper.getMainLooper());

    public static boolean post(Runnable r) {
        return handler.post(r);
    }

    public static boolean postDelayed(Runnable r, long delayMillis) {
        return handler.postDelayed(r, delayMillis);
    }
}
