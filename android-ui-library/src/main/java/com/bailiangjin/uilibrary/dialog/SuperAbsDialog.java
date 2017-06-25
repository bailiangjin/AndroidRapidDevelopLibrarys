package com.bailiangjin.uilibrary.dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
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

    protected AlertDialog alertDialog;

    protected View rootView;

    private boolean isCancelable;

    protected LayoutInflater layoutInflater;

    public SuperAbsDialog(Context context) {
        this.context = context;
        alertDialog = new AlertDialog.Builder(context)
                .setCancelable(true).create();
        alertDialog.show();
        layoutInflater = LayoutInflater.from(alertDialog.getContext());
        Window window = alertDialog.getWindow();
        rootView = layoutInflater.inflate(getRootLayoutResId(), null);
        bindRootView(rootView);
        window.setContentView(rootView);

    }

    public Context getContext() {
        return this.context;
    }



    public SuperAbsDialog setCancelable(boolean isCancelable) {
        this.isCancelable = isCancelable;
        alertDialog.setCancelable(isCancelable);
        return this;
    }

    public boolean isCancelable() {
        return isCancelable;
    }


    public void show() {
        alertDialog.show();
    }

    public void dismiss() {
        alertDialog.dismiss();
    }


    //子类需覆盖的抽象方法

    protected abstract int getRootLayoutResId();

    protected abstract void bindRootView(View rootView);



}
