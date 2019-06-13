package com.bailiangjin.uilibrary.dialog;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bailiangjin.uilibrary.R;


/**
 * 通用的可自定义内容的Dialog
 * Author:  liangjin.bai
 * Email: bailiangjin@gmail.com
 * Create Time: 2016/10/15 17:32
 */
public abstract class AbsTitleDialog extends SuperAbsDialog {

    /**
     * 默认标题
     */
    private String titleText = "提示";

    private TextView tv_dialog_title;

    private FrameLayout fl_container;


    public AbsTitleDialog(Context context) {
        super(context);
        /**
         * 子类View事件绑定处理回调
         */
        bindSubView(rootView);
    }

    @Override
    protected void bindRootView(View rootView) {
        tv_dialog_title = (TextView) rootView.findViewById(R.id.tv_dialog_title);
        tv_dialog_title.setText(titleText);
        tv_dialog_title.setVisibility(isNoTitle() ? View.GONE : View.VISIBLE);
        fl_container = (FrameLayout) rootView.findViewById(R.id.fl_container);
        View childView = layoutInflater.inflate(getLayoutResId(), null);
        fl_container.removeAllViews();
        fl_container.addView(childView);
    }


    public AbsTitleDialog setTitleText(String titleText) {
        tv_dialog_title.setText(titleText);
        tv_dialog_title.invalidate();
        return this;
    }

    public AbsTitleDialog showTitle() {
        tv_dialog_title.setVisibility(View.VISIBLE);
        return this;
    }

    public AbsTitleDialog hideTitle() {
        tv_dialog_title.setVisibility(View.GONE);
        return this;
    }

    public String getTitleText() {
        return titleText;
    }

    @Override
    protected int getRootLayoutResId() {
        return R.layout.dialog_abstract;
    }


    //子类需覆盖的抽象方法

    /**
     * 返回自定义部分的 布局 资源id 建议布局 根布局使用FrameLayout 标签  第二层 布局作为实际的根布局 在第二层布局中设置实际的dialog弹窗大小
     *
     * @return
     */
    protected abstract int getLayoutResId();

    /**
     * 子类 View回调 可以在此处完成View事件的绑定和数据填充等处理
     *
     * @param view
     */
    protected abstract void bindSubView(View view);


    /**
     * 是否无标题 return true 则隐藏标题（即默认的提示文字）
     *
     * @return
     */
    protected abstract boolean isNoTitle();

}
