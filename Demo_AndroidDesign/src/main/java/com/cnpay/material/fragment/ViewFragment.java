package com.cnpay.material.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cnpay.material.R;

/**
 * 包   名:     com.cnpay.material.fragment
 * 类   名:     ViewFragment
 * 版权所有:     版权所有(C)2010-2016
 * 公   司:     深圳华夏通宝信息技术有限公司
 * 版   本:          V1.0
 * 时   间:     2016/12/15 0015 15:55
 * 作   者:     yuyucheng
 */
public class ViewFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout=inflater.inflate(R.layout.fragment_view,container);
        return layout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
