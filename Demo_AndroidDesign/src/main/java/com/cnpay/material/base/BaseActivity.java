package com.cnpay.material.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

/**
 * 包   名:     com.cnpay.material.base
 * 类   名:     BaseActivity
 * 版权所有:     版权所有(C)2010-2016
 * 公   司:     深圳华夏通宝信息技术有限公司
 * 版   本:          V1.0
 * 时   间:     2016/12/1 0001 14:58
 * 作   者:     yuyucheng
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**去掉默认导航栏*/
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
    }
}
