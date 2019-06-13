package com.bailiangjin.uilibrary.dialog.bottompopupdialog;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.bailiangjin.uilibrary.dialog.bottompopupdialog.adapter.DialogBtnAdapter;
import com.bailiangjin.uilibrary.dialog.bottompopupdialog.listener.BtnClickListener;
import com.bailiangjin.uilibrary.dialog.popupwindow.AbsPopupWindow;
import com.bailiangjin.uilibrary.R;
import com.bailiangjin.uilibrary.dialog.bottompopupdialog.adapter.DialogBtnAdapter;
import com.bailiangjin.uilibrary.dialog.bottompopupdialog.listener.BtnClickListener;
import com.bailiangjin.uilibrary.dialog.bottompopupdialog.model.DialogBtnItem;
import com.bailiangjin.uilibrary.dialog.popupwindow.AbsPopupWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * 底部 弹起按钮组 PopupWindow 组件
 * Created by bailiangjin on 2017/5/6.
 */

public class BottomBtnPopupWindow extends AbsPopupWindow {
    private RecyclerView recyclerView;
    private DialogBtnAdapter dialogBtnAdapter;
    protected List<DialogBtnItem> dialogBtnItemList;


    public BottomBtnPopupWindow(Builder builder) {
        super(builder.mContext);
        this.dialogBtnItemList = builder.dialogBtnItemList;
        initData();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.common_popup_dialog;
    }

    @Override
    protected void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
    }

    protected void initData() {
        for(DialogBtnItem item :dialogBtnItemList){
            if (item.isCancelItem()) {
                item.setBtnClickListener(new BtnClickListener() {
                    @Override
                    public void onItemClick() {
                        dismiss();
                    }
                });
            }else {
              final BtnClickListener btnClickListener=  item.getBtnClickListener();
                if(null!=btnClickListener){
                    item.setBtnClickListener(new BtnClickListener() {
                        @Override
                        public void onItemClick() {
                            dismiss();
                            btnClickListener.onItemClick();
                        }
                    });
                }
            }
        }
        initOrUpdateAdapter(dialogBtnItemList);
    }

    private void initOrUpdateAdapter(List<DialogBtnItem> dialogBtnItemList) {
        if (null == dialogBtnItemList || dialogBtnItemList.isEmpty()) {
            Log.e(getClass().getName(), "btn list is empty");
            return;
        }
        parseStyle(dialogBtnItemList, 0, dialogBtnItemList.size() - 1);
        if (null == dialogBtnAdapter) {
            dialogBtnAdapter = new DialogBtnAdapter(mContext);
            dialogBtnAdapter.setData(dialogBtnItemList);
            recyclerView.setAdapter(dialogBtnAdapter);
        } else {
            dialogBtnAdapter.setData(dialogBtnItemList);
            dialogBtnAdapter.notifyDataSetChanged();
        }
    }


    /**
     * 解析按钮 样式
     *
     * @param dialogBtnItemList list
     * @param start             起始位置
     * @param end               结束位置
     */
    private void parseStyle(List<DialogBtnItem> dialogBtnItemList, int start, int end) {
        if (start > end) {
            return;
        }
        if (end == start) {
            dialogBtnItemList.get(start).setStyleType(DialogBtnItem.StyleType.FULL);
        }

        for (int i = start; i <= end; i++) {
            DialogBtnItem curItem = dialogBtnItemList.get(i);
            if (DialogBtnItem.NumberType.SINGLE == curItem.getNumberType()) {
                curItem.setStyleType(DialogBtnItem.StyleType.FULL);
                parseStyle(dialogBtnItemList, start, i - 1);
                parseStyle(dialogBtnItemList, i + 1, end);
                return;
            } else {
                if (start == i) {
                    //顶部
                    curItem.setStyleType(DialogBtnItem.StyleType.TOP);
                } else if (end == i) {
                    //底部
                    curItem.setStyleType(DialogBtnItem.StyleType.BOTTOM);
                } else {
                    //中间
                    curItem.setStyleType(DialogBtnItem.StyleType.MIDDLE);
                }
            }


        }

    }

    public static class Builder {
        Context mContext;
        protected List<DialogBtnItem> dialogBtnItemList;
        boolean isHasCancel;


        public Builder() {
            this.dialogBtnItemList = new ArrayList<>();
            this.isHasCancel = true;

        }

        public BottomBtnPopupWindow build(Context context) {
            this.mContext = context;
            if (isHasCancel) {
                dialogBtnItemList.add(new DialogBtnItem("取消", DialogBtnItem.NumberType.SINGLE, null).setCancelItem(true));
            }
            return new BottomBtnPopupWindow(this);
        }

        public Builder addGroupItem(String name, BtnClickListener btnClickListener) {
            dialogBtnItemList.add(new DialogBtnItem(name, DialogBtnItem.NumberType.GROUP, btnClickListener));
            return this;
        }

        public Builder addSingleItem(String name, BtnClickListener btnClickListener) {
            dialogBtnItemList.add(new DialogBtnItem(name, DialogBtnItem.NumberType.SINGLE, btnClickListener));
            return this;
        }

        public Builder setHasCancle(boolean isHasCancel) {
            this.isHasCancel = isHasCancel;
            return this;
        }


    }
}