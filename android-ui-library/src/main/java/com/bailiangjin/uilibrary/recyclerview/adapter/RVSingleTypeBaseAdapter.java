package com.bailiangjin.uilibrary.recyclerview.adapter;

import android.content.Context;

import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

/**
 * 单种类型条目的 RecyclerViewBaseAdapter
 * Created by bailiangjin on 2016/12/20.
 */
public abstract class RVSingleTypeBaseAdapter<T> extends RVMultiTypeBaseAdapter<T> implements ItemViewDelegate<T>{

    public RVSingleTypeBaseAdapter(Context context) {
        super(context);
        addItemViewDelegate(new ItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return RVSingleTypeBaseAdapter.this.getItemViewLayoutId();
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(ViewHolder holder, T item, int position) {

                RVSingleTypeBaseAdapter.this.convert(holder,item,position);
            }
        });

    }

    /**
     * 单类型 永远返回true
     * 子类不需要Override 该方法
     * @param item
     * @param position
     * @return
     */
    @Override
    public final boolean isForViewType(T item, int position) {
        return true;
    }
}
