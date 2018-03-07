package com.zhy.adapter.recyclerview.base;


/**
 * item delegate 接口
 *
 * @author bailiangjin
 *
 * @date 2018/3/7
 */
public interface ItemViewDelegate<T> {


    /**
     * 获取 itemView使用的layoutResId
     *
     * @return
     */
    int getItemViewLayoutId();

    /**
     * 类型匹配
     *
     * @param item
     * @param position
     * @return
     */
    boolean isForViewType(T item, int position);

    /**
     * 数据填充
     *
     * @param holder
     * @param item
     * @param position
     */
    void convert(ViewHolder holder, T item, int position);

}

