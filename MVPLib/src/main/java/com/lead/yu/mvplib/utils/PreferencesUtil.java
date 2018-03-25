package com.lead.yu.mvplib.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.Map;

/**
 * SharedPreferences 工具
 *
 * Created by yuyucheng on 2018/3/25.
 */

public class PreferencesUtil {
    /**
     * 保存一个实体
     *
     * @author liyuanming
     * @param context
     * @param fileName
     * @param key
     * @param value
     * @throws IOException
     */
    public static void saveObj(Context context, String fileName, String key, Object value) throws IOException {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(value);
        String objString = new String(android.util.Base64.encode(byteArrayOutputStream.toByteArray(), android.util.Base64.DEFAULT));
        sp.edit().putString(key, objString).commit();
        objectOutputStream.close();
    }

    /**
     * 取出一个实体
     *
     * @author liyuanming
     * @param context
     * @param fileName
     * @param key
     * @return
     * @throws StreamCorruptedException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object getObj(Context context, String fileName, String key) throws StreamCorruptedException, IOException,
            ClassNotFoundException {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        String str = sp.getString(key, "");
        if (str.length() <= 0)
            return null;
        Object obj = null;
        byte[] mobileBytes = android.util.Base64.decode(str.toString().getBytes(), android.util.Base64.DEFAULT);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(mobileBytes);
        ObjectInputStream objectInputStream;
        objectInputStream = new ObjectInputStream(byteArrayInputStream);
        obj = objectInputStream.readObject();
        objectInputStream.close();
        return obj;
    }

    /**
     * 添加一个String到
     */
    public static boolean putString(String fileName, Context context, String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    /**
     * 取得一个String 的数据
     */
    public static String getString(String fileName, Context context, String key) {
        return getString(fileName, context, key, null);
    }

    /**
     * 带默认值的
     */
    public static String getString(String fileName, Context context, String key, String defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return settings.getString(key, defaultValue);
    }

    /**
     * 存储int类型数据
     */
    public static boolean putInt(String fileName, Context context, String key, int value) {
        SharedPreferences settings = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    /**
     * 获取int类型数据
     */
    public static int getInt(String fileName, Context context, String key) {
        return getInt(fileName, context, key, -1);
    }

    /**
     * 获取int类型数据
     */
    public static int getInt(String fileName, Context context, String key, int defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return settings.getInt(key, defaultValue);
    }

    /**
     * 存储long类型数据
     */
    public static boolean putLong(String fileName, Context context, String key, long value) {
        SharedPreferences settings = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    /**
     * 获取long类型数据
     */
    public static long getLong(String fileName, Context context, String key) {
        return getLong(fileName, context, key, -1);
    }

    /**
     * 获取long类型数据
     */
    public static long getLong(String fileName, Context context, String key, long defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return settings.getLong(key, defaultValue);
    }

    /**
     * 存储Float类型数据
     */
    public static boolean putFloat(String fileName, Context context, String key, float value) {
        SharedPreferences settings = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    /**
     * 获取Float类型数据
     */
    public static float getFloat(String fileName, Context context, String key) {
        return getFloat(fileName, context, key, -1);
    }

    /**
     * 获取Float类型数据 没有就默认
     */
    public static float getFloat(String fileName, Context context, String key, float defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return settings.getFloat(key, defaultValue);
    }

    /**
     * 存储的布尔类型数据
     */
    public static boolean putBoolean(String fileName, Context context, String key, boolean value) {
        SharedPreferences settings = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    /**
     * 获取本地存储的布尔类型数据
     */
    public static boolean getBoolean(String fileName, Context context, String key) {
        return getBoolean(fileName, context, key, false);
    }

    /**
     * 获取本地存储的布尔类型数据,如果没有则赋默认值
     */
    public static boolean getBoolean(String fileName, Context context, String key, boolean defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return settings.getBoolean(key, defaultValue);
    }

    /**
     * 把集合转换成字符串
     *
     * @param
     * @return
     * @throws IOException
     */
    public static String mapToString(Map<String, Object> WeatherMap) throws IOException {
        // 实例化一个ByteArrayOutputStream对象，用来装载压缩后的字节文件。
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // 然后将得到的字符数据装载到ObjectOutputStream
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        // writeObject 方法负责写入特定类的对象的状态，以便相应的 readObject 方法可以还原它
        objectOutputStream.writeObject(WeatherMap);
        // 最后，用Base64.encode将字节文件转换成Base64编码保存在String中
        String WeatherListString = new String(android.util.Base64.encode(byteArrayOutputStream.toByteArray(), android.util.Base64.DEFAULT));
        // 关闭objectOutputStream
        objectOutputStream.close();
        return WeatherListString;
    }

    /**
     * 把字符串转换成集合
     *
     * @param WeatherListString
     * @return
     * @throws StreamCorruptedException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> stringToMap(String WeatherListString) throws StreamCorruptedException, IOException,
            ClassNotFoundException {
        byte[] mobileBytes = android.util.Base64.decode(WeatherListString.getBytes(), android.util.Base64.DEFAULT);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(mobileBytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Map<String, Object> weatherMap = (Map<String, Object>) objectInputStream.readObject();
        objectInputStream.close();
        return weatherMap;
    }

    /**
     * 清空本地数据文件
     */
    public static void clearPreferences(String fileName, Context context) {
        SharedPreferences settings = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.commit();
    }
}
