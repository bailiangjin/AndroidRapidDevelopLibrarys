package com.bailiangjin.uilibrary.dialog.middlelistpopupwindow;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.bailiangjin.uilibrary.dialog.bottompopupdialog.adapter.DialogBtnAdapter;
import com.bailiangjin.uilibrary.dialog.bottompopupdialog.listener.BtnClickListener;
import com.bailiangjin.uilibrary.R;
import com.bailiangjin.uilibrary.dialog.bottompopupdialog.model.DialogBtnItem;
import com.bailiangjin.uilibrary.dialog.popupwindow.AbsPopupWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * 可以自定义添加item的 中部显示的 列表 Dialog
 * Created by bailiangjin on 2017/5/9.
 */

public class MiddleListPopupWindow extends AbsPopupWindow {

    private RecyclerView recyclerView;
    private DialogBtnAdapter dialogBtnAdapter;
    protected List<DialogBtnItem> dialogBtnItemList;


    public MiddleListPopupWindow(Builder builder) {
        super(builder.mContext);
        this.dialogBtnItemList = builder.dialogBtnItemList;
        initData();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.middle_list_popup_window;
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

        public Builder() {
            this.dialogBtnItemList = new ArrayList<>();
        }

        public MiddleListPopupWindow build(Context context) {
            this.mContext = context;
            return new MiddleListPopupWindow(this);
        }

        public Builder addItem(String name, BtnClickListener btnClickListener) {
            dialogBtnItemList.add(new DialogBtnItem(name, DialogBtnItem.NumberType.GROUP, btnClickListener));
            return this;
        }

    }
}
