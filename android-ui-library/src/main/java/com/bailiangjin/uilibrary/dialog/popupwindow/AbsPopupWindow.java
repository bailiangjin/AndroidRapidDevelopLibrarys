package com.bailiangjin.uilibrary.dialog.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.PopupWindow;


public abstract class AbsPopupWindow {
    public PopupWindow mPopupWindow;

    public PopupWindow getmPopupWindow() {
        return mPopupWindow;
    }

    protected Context mContext;
    private View mView;

    @SuppressWarnings("deprecation")
    public AbsPopupWindow(Context context) {
        mContext = context;
        mPopupWindow = new PopupWindow(context);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setWidth(WindowManager.LayoutParams.FILL_PARENT);
        mPopupWindow.setHeight(WindowManager.LayoutParams.FILL_PARENT);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
//        mPopupWindow.setAnimationStyle(R.style.AnimBottom);
        mView=getDialogView();
        mPopupWindow.setContentView(mView);
        initView(mView);
        mPopupWindow.getContentView().setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mPopupWindow.setFocusable(false);
                mPopupWindow.dismiss();
                return true;
            }
        });

    }

    public void dismiss() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    /**
     * 显示到某个view上
     * @param aimView 要显示到该view上
     */
    public void show(View aimView) {
        // 第一个参数是要将PopupWindow放到的View，第二个参数是位置，第三第四是偏移值
        mPopupWindow.showAtLocation(aimView, Gravity.CENTER, 0, 0);
    }

    /**
     * 默认显示 到屏幕中央
     */
    public void show() {
        View v=((Activity)mContext).getWindow().getDecorView();
        // 第一个参数是要将PopupWindow放到的View，第二个参数是位置，第三第四是偏移值
        mPopupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
    }


    private View getDialogView(){
        View dialogView = LayoutInflater.from(mContext).inflate(getLayoutResId(), null);

        return dialogView;
    }

    protected abstract int getLayoutResId();
    protected abstract void initView(View view);
}
