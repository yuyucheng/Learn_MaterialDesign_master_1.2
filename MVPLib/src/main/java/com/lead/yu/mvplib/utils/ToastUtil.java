package com.lead.yu.mvplib.utils;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lead.yu.mvplib.R;

/**
 * Created by yuyucheng on 2018/3/25.
 */

public class ToastUtil {
    /** 上下文. */
    private static Context mContext = null;

    /** 显示Toast. */
    public static final int SHOW_TOAST = 0;

    /**
     * 主要Handler类，在线程中可用
     * what：0.提示文本信息
     */
    private static Handler baseHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_TOAST:
                    showToast(mContext,msg.getData().getString("TEXT"));
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 描述：Toast提示文本.
     * @param text  文本
     */
    public static void showToastLong(Context context,String text) {
        mContext = context;
        if(!StrUtil.isEmpty(text)){
            Toast.makeText(context, text, Toast.LENGTH_LONG).show();
        }

    }

    /**
     * 描述：Toast提示文本.
     * @param text  文本
     */
    public static void showToast(Context context,String text) {
        mContext = context;
        if(!StrUtil.isEmpty(text)){
            Toast.makeText(context,text, Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 描述：Toast提示文本.
     * @param resId  文本的资源ID
     */
    public static void showToast(Context context,int resId) {
        mContext = context;
        Toast.makeText(context,""+context.getResources().getText(resId), Toast.LENGTH_SHORT).show();
    }

    /**
     * 描述：在线程中提示文本信息.
     * @param resId 要提示的字符串资源ID，消息what值为0,
     */
    public static void showToastInThread(Context context,int resId) {
        mContext = context;
        Message msg = baseHandler.obtainMessage(SHOW_TOAST);
        Bundle bundle = new Bundle();
        bundle.putString("TEXT", context.getResources().getString(resId));
        msg.setData(bundle);
        baseHandler.sendMessage(msg);
    }

    /**
     * 描述：在线程中提示文本信息.
     * @param text 消息what值为0
     */
    public static void showToastInThread(Context context,String text) {
        mContext = context;
        Message msg = baseHandler.obtainMessage(SHOW_TOAST);
        Bundle bundle = new Bundle();
        bundle.putString("TEXT", text);
        msg.setData(bundle);
        baseHandler.sendMessage(msg);
    }

    /**
     * 自定义中间吐司
     * @param context   context
     * @param text      信息
     */
    public static  void  showCenterToast(Context context,String text,int layout,int viewID) {
        View view = LayoutInflater.from(context).inflate(layout, null);
        TextView infoView = (TextView) view.findViewById(viewID);
        infoView.setText(text);
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }
}
