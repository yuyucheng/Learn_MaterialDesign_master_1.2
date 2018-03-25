package com.cnpay.material.activity.rxjava.model;

/**
 * @date: 2018/3/7 0007 上午 11:45
 * @author: yuyucheng
 */
public class Translation2 {
    private int status;
    private content content;
    private static class content {
        private String from;
        private String to;
        private String vendor;
        private String out;
        private int errNo;
    }

    //定义 输出返回数据 的方法
    public String show() {

        return ("第2次翻译=" + content.out);

    }
}
