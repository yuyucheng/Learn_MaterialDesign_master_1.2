package com.lead.yu.mvplib;

import com.lead.yu.mvplib.utils.AppBaseUtil;

/**
 * Created by yuyucheng on 2018/3/25.
 */

public interface DBConfig {
    String BASE_PATH = AppBaseUtil.getSDCardPath() + "/learn";
    String FILE_PATH_IMAGE = BASE_PATH + "/image";
    String FILE_PATH_FILE = BASE_PATH + "/apk";
}
