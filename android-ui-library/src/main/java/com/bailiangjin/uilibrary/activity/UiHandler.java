package com.bailiangjin.uilibrary.activity;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * UI线程 Handler
 *
 * @author bailiangjin
 *
 * @date 2017/11/9
 */
public class UiHandler extends Handler {

    /**
     * 回调接口，消息传递给注册者
     */
    private UIHandlerListener uiHandlerListener;

    public UiHandler(Looper looper) {
        super(looper);
    }

    public UiHandler(Looper looper, UIHandlerListener handleMsgListener) {
        super(looper);
        this.uiHandlerListener = handleMsgListener;
    }

    public void setListener(UIHandlerListener uiHandlerListener) {
        this.uiHandlerListener = uiHandlerListener;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (uiHandlerListener != null) {
            // 有消息，就传递
            uiHandlerListener.handleMessage(msg);
        }
    }
}
