package com.cnpay.material.retrofit;

import com.google.gson.annotations.SerializedName;

/**
 * 封装的网络返回
 *
 * @date: 2018/3/7 0007 下午 4:27
 * @author: yuyucheng
 */
public class BaseResponse<E> {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private E data;

    public boolean isSuccess() {
        return code == 0;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

}
