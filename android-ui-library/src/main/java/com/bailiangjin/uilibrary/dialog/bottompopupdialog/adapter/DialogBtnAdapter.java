package com.bailiangjin.uilibrary.dialog.bottompopupdialog.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.bailiangjin.uilibrary.R;
import com.bailiangjin.uilibrary.dialog.bottompopupdialog.model.DialogBtnItem;
import com.bailiangjin.uilibrary.recyclerview.adapter.RVSingleTypeBaseAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

/**
 * Created by bailiangjin on 2017/5/6.
 */

public class DialogBtnAdapter extends RVSingleTypeBaseAdapter<DialogBtnItem> {
    public DialogBtnAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemLayoutResId() {
        return R.layout.item_popup_dialog_btn;
    }

    @Override
    protected void convert(ViewHolder holder, final DialogBtnItem item, int position) {

        View rootView = holder.getView(R.id.rl_root);
        View v_line = holder.getView(R.id.v_line);
        View v_gap = holder.getView(R.id.v_gap);
        TextView tv_name = holder.getView(R.id.tv_name);
        tv_name.setText(item.getName());

        v_line.setVisibility(DialogBtnItem.StyleType.MIDDLE == item.getStyleType() || DialogBtnItem.StyleType.BOTTOM == item.getStyleType() ? View.VISIBLE : View.GONE);
        v_gap.setVisibility(DialogBtnItem.StyleType.FULL == item.getStyleType() || DialogBtnItem.StyleType.TOP == item.getStyleType() ? View.VISIBLE : View.GONE);

        switch (item.getStyleType()) {
            case TOP:
                tv_name.setBackgroundResource(R.drawable.bottom_popup_dialog_btn_top_bg);
                break;
            case MIDDLE:
                tv_name.setBackgroundResource(R.drawable.bottom_popup_dialog_btn_middle_bg);
                break;
            case BOTTOM:
                tv_name.setBackgroundResource(R.drawable.bottom_popup_dialog_btn_botoom_bg);
                break;
            case FULL:
                tv_name.setBackgroundResource(R.drawable.bottom_popup_dialog_btn_single_bg);
                break;
            default:
                break;
        }

        if (null != item.getBtnClickListener()) {
            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item.getBtnClickListener().onItemClick();
                }
            });
        }
    }

}
