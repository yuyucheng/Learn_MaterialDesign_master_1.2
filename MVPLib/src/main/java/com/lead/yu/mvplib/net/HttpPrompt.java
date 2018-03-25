package com.lead.yu.mvplib.net;

/**
 * 常用网络请求状态 bs
 *
 * Created by yuyucheng on 2018/3/25.
 */

public class HttpPrompt {
    /** The Constant CONNECTEXCEPTION. */
    public static final String CONNECTEXCEPTION = "网络连接不可用，请稍后再试";

    /** The Constant UNKNOWNHOSTEXCEPTION. */
    public static final String UNKNOWNHOSTEXCEPTION = "连接远程地址失败";

    /** The Constant SOCKETEXCEPTION. */
    public static final String SOCKETEXCEPTION = "网络连接出错，请重试";

    /** The Constant SOCKETTIMEOUTEXCEPTION. */
    public static final String SOCKETTIMEOUTEXCEPTION = "连接超时，请重试";

    /** The Constant NULLPOINTEREXCEPTION. */
    public static final String NULLPOINTEREXCEPTION = "抱歉，远程服务出错了";

    /** The Constant NULLMESSAGEEXCEPTION. */
    public static final String NULLMESSAGEEXCEPTION = "抱歉，程序出错了";

    /** The Constant CLIENTPROTOCOLEXCEPTION. */
    public static final String CLIENTPROTOCOLEXCEPTION = "Http请求参数错误";

    /** 参数个数不够. */
    public static final String MISSINGPARAMETERS = "参数没有包含足够的值";

    /** The Constant REMOTESERVICEEXCEPTION. */
    public static final String REMOTESERVICEEXCEPTION = "抱歉，远程服务出错了";

    /** 页面未找到. */
    public static final String NOT_FOUND_EXCEPTION = "页面未找到";

    /** 其他异常. */
    public static final String UNTREATED_EXCEPTION = "未处理的异常";
}
