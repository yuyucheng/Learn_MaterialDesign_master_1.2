package com.cnpay.material.entity;

/**
 * 包   名:     com.cnpay.material.entity
 * 类   名:     User
 * 版权所有:     版权所有(C)2010-2016
 * 公   司:     深圳华夏通宝信息技术有限公司
 * 版   本:          V1.0
 * 时   间:     2016/12/1 0001 14:59
 * 作   者:     yuyucheng
 */
public class User {
    public User(String data) {
        this.data = data;
    }

    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
