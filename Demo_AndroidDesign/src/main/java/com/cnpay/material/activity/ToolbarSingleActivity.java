package com.cnpay.material.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cnpay.material.R;
import com.cnpay.material.base.BaseActivity;

/**
 * 包   名:     com.cnpay.material.activity
 * 类   名:     ToolbarSingleActivity
 * 版权所有:     版权所有(C)2010-2016
 * 公   司:     深圳华夏通宝信息技术有限公司
 * 版   本:          V1.0
 * 时   间:     2016/12/5 0005 15:01
 * 作   者:     yuyucheng
 */
public class ToolbarSingleActivity extends BaseActivity{

    String about="AppBarLayout 是继承LinerLayout实现的一个ViewGroup容器组件，" +
            "它是为了Material Design设计的App Bar，支持手势滑动操作(需要跟CoordinatorLayout配合使用)。" +
            "默认的AppBarLayout是垂直方向的，它的作用是把AppBarLayout包裹的内容都作为AppBar。";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar_single);


    }
}
