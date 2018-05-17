package com.bailiangjin.uilibrary.recyclerview.interfaze;

/**
 * 下拉刷新回调
 *
 * @author bailiangjin
 * @date 2018/5/9
 */

public interface IRefreshCallback {

    /**
     * 开始刷新回调 用于开始动画
     */
    void onStart();

    /**
     * 刷新成功
     */
    void onSuccess();

    /**
     * 刷新失败
     */
    void Failed();
}
