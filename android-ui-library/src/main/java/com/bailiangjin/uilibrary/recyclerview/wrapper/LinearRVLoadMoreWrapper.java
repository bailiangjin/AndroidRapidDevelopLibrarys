package com.bailiangjin.uilibrary.recyclerview.wrapper;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bailiangjin.uilibrary.R;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.LoadMoreWrapper;

/**
 *  RecyclerView list 布局 加载更多包装器 解决 baseadapter 库中 首次加载触发加载更多问题
 * Created by bailiangjin on 2017/2/7.
 */


/**
 * RecyclerView list 布局 加载更多包装器 解决 baseadapter 库中 首次加载触发加载更多问题
 * Created by bailiangjin on 2017/2/7.
 */

public class LinearRVLoadMoreWrapper<T> extends LoadMoreWrapper<T> {
    protected RecyclerView recyclerView;

    private boolean isHasMoreData = true;


    public LinearRVLoadMoreWrapper(RecyclerView.Adapter adapter, RecyclerView recyclerView) {
        super(adapter);
        if (null == recyclerView || !(recyclerView.getLayoutManager() instanceof LinearLayoutManager)) {
            throw new RuntimeException("recyclerView is null or the layoutManager of recyclerview is not instance of LinearLayoutManager");
        }
        this.recyclerView = recyclerView;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (isShowLoadMore(position)) {
            if (holder instanceof ViewHolder) {
                ViewHolder viewHolder = (ViewHolder) holder;

                View loading_more = viewHolder.getView(R.id.loading_more);
                View no_more_data = viewHolder.getView(R.id.tv_no_more_data);

                loading_more.setVisibility(isHasMoreData ? View.VISIBLE : View.GONE);
                no_more_data.setVisibility(!isHasMoreData ? View.VISIBLE : View.GONE);
            }

            if (mOnLoadMoreListener != null && isHasMoreData) {
                mOnLoadMoreListener.onLoadMoreRequested();
            }
            return;
        }
        mInnerAdapter.onBindViewHolder(holder, position);
    }

    @Override
    public int getLoadMoreLayoutResId() {
        return R.layout.loading_more;
    }


    /**
     * 根据是否有更多数据 设置加载更多状态
     *
     * @param isHasMoreData
     */
    public void setHasMoreData(boolean isHasMoreData) {
        this.isHasMoreData = isHasMoreData;
        notifyDataChanged();
    }

}

