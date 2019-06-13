package com.bailiangjin.uilibrary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;


/**
 * 通用的可自定义内容的Dialog
 * Author:  liangjin.bai
 * Email: bailiangjin@gmail.com
 * Create Time: 2016/10/15 17:32
 */
public abstract class SuperAbsDialog {

    private Context context;

    protected Dialog dialog;

    protected View rootView;

    private boolean isCancelable;

    protected LayoutInflater layoutInflater;

    public SuperAbsDialog(Context context) {
        this.context = context;
        dialog = new Dialog(context);
        dialog.setCancelable(true);
        layoutInflater = LayoutInflater.from(dialog.getContext());
        Window window = dialog.getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        rootView = layoutInflater.inflate(getRootLayoutResId(), null);
        bindRootView(rootView);
        window.setContentView(rootView);
        dialog.show();


    }

    public Context getContext() {
        return this.context;
    }



    public SuperAbsDialog setCancelable(boolean isCancelable) {
        this.isCancelable = isCancelable;
        dialog.setCancelable(isCancelable);
        return this;
    }

    public boolean isCancelable() {
        return isCancelable;
    }


    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }


    //子类需覆盖的抽象方法

    protected abstract int getRootLayoutResId();

    protected abstract void bindRootView(View rootView);



}
