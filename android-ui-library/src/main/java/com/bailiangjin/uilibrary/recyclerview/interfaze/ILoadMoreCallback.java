package com.bailiangjin.uilibrary.recyclerview.interfaze;

/**
 * 加载更多结果回调
 *
 * @author bailiangjin
 * @date 2018/5/17
 */
public interface ILoadMoreCallback {

    /**
     * 加载更多结果
     *
     * @param isHasMoreData 是否有更多数据
     */
    void onLoadResult(boolean isHasMoreData);
}
