package com.lead.yu.mvplib.task;

import java.util.List;

/**
 * TaskListListener
 *
 * Created by yuyucheng on 2018/3/25.
 */

public abstract class TaskListListener extends TaskListener{
    /**
     * Gets the list.
     *
     * @return 返回的结果列表
     */
    public abstract List<?> getList();

    /**
     * 描述：执行完成后回调.
     * 不管成功与否都会执行
     * @param paramList 返回的List
     */
    public abstract void update(List<?> paramList);
}
