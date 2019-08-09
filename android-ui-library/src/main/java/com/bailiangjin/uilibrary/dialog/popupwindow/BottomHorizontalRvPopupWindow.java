//package com.bailiangjin.uilibrary.dialog.popupwindow;
//
//import android.content.Context;
//import android.support.annotation.StringRes;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.bailiangjin.uilibrary.R;
//
//
///**
// * 底部通用的 横向RecyclerView PopupWindow
// *
// * @author bailiangjin
// * @date 2019-08-09
// */
//public class BottomHorizontalRvPopupWindow extends AbsPopupWindow {
//
//    private TextView tvTitle;
//    private ImageView ivClose;
//
//    private RecyclerView recyclerView;
//
//    private @StringRes
//    int mTitleStringResId;
//
//    private RecyclerView.Adapter mAdapter;
//
//
//    public BottomHorizontalRvPopupWindow(Context context, @StringRes int titleStringResId, RecyclerView.Adapter adapter) {
//        super(context);
//        mTitleStringResId = titleStringResId;
//        mAdapter = adapter;
//        initData();
//    }
//
//    @Override
//    protected int getLayoutResId() {
//        return R.layout.bottom_horizontal_rv_popup_window;
//    }
//
//    @Override
//    protected void initView(View view) {
//        tvTitle = view.findViewById(R.id.tv_title);
//        recyclerView = view.findViewById(R.id.recyclerView);
//        ivClose = view.findViewById(R.id.iv_close);
//    }
//
//    private void initData() {
//        if (mTitleStringResId != 0) {
//            tvTitle.setText(mTitleStringResId);
//        }
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(mAdapter);
//        ivClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });
//    }
//}
