package com.cnpay.material.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;

/**
 * 包   名:     com.cnpay.material.utils
 * 类   名:     ActivityUtils
 * 版权所有:     版权所有(C)2010-2016
 * 公   司:     深圳华夏通宝信息技术有限公司
 * 版   本:          V1.0
 * 时   间:     2016/12/1 0001 15:51
 * 作   者:     yuyucheng
 */
public class ActivityUtils {

    private ActivityUtils() {}

    public static boolean isAlive(Activity activity) {
        return activity != null && !(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed()) && !activity.isFinishing();
    }

    public static void recreate(@NonNull Activity activity) {
        /*3.0以上*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            activity.recreate();
        } else {
            Intent intent = activity.getIntent();
            intent.setClass(activity, activity.getClass());
            activity.startActivity(intent);
            activity.finish();
            activity.overridePendingTransition(0, 0);
        }
    }

    public static void recreateDelayed(@NonNull final Activity activity) {
        HandlerUtils.post(new Runnable() {

            @Override
            public void run() {
                recreate(activity);
            }

        });
    }

}
