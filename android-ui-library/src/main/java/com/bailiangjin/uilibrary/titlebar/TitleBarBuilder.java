package com.bailiangjin.uilibrary.titlebar;

import android.app.Activity;
import android.content.res.Resources;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bailiangjin.uilibrary.R;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 标题栏 构建类
 *
 * @author bailiangjin
 */
public class TitleBarBuilder {

    private static final int defaultBgResId = R.drawable.title_gradient_bg;
    private static final int defaultBackIconResId = R.drawable.icon_back;


    private Map<String, MyMenuItem> itemLinkedHashMap = new LinkedHashMap<>();

    private Toolbar mToolbar;
    private TextView tvTitle;

    private TitlePosition titlePosition = TitlePosition.LEFT;
    private String titleText="";

    public TitleBarBuilder(final Activity activity, Toolbar toolbar) {
        mToolbar = toolbar;

        tvTitle = (TextView) toolbar.findViewById(R.id.tv_title_toolbar);
        mToolbar.setBackgroundResource(defaultBgResId);

        //默认 返回键按钮
        mToolbar.setNavigationIcon(defaultBackIconResId);
        //默认点击事件 finish Activity
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onBackPressed();
            }
        });

        mToolbar.setTitleTextAppearance(activity, R.style.title_tv_style);
        mToolbar.inflateMenu(R.menu.base_toolbar_menu);//设置右上角填充菜单
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                MyMenuItem myMenuItem = itemLinkedHashMap.get(item.getTitle());
                if (null != myMenuItem && null != myMenuItem.getItemClickListener()) {
                    myMenuItem.getItemClickListener().onClick();
                    return true;
                }
                return false;
            }
        });
    }


    public Toolbar build() {
        setMyMenu(mToolbar.getMenu());
        return mToolbar;
    }


    /**
     * 设置标题文字位置 靠左或居中
     *
     * @param titlePosition
     * @return
     */
    public TitleBarBuilder setTitleSide(TitlePosition titlePosition) {
        this.titlePosition = titlePosition;
        setTitleText(titleText, titlePosition);
        return this;
    }

    /**
     * 设置标题栏文字 默认居中显示
     *
     * @param title
     * @return
     */
    public TitleBarBuilder setTitleText(String title) {
        this.titleText=title;
        setTitleText(titleText, titlePosition);
        return this;
    }


    /**
     * 设置标题栏文字
     *
     * @param title    标题文字
     * @param position 文字位置
     * @return
     */
    private TitleBarBuilder setTitleText(String title, TitlePosition position) {

        switch (position) {
            case LEFT:
                tvTitle.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
                tvTitle.setText(title);
                break;
            case MIDDLE:

                tvTitle.setGravity(Gravity.CENTER|Gravity.CENTER_VERTICAL);
                tvTitle.setText(title);

                break;
            default:
                tvTitle.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
                tvTitle.setText(title);
                break;
        }
        return this;
    }


    public TitleBarBuilder setIconVisibility(String title, boolean visibility) {
        if (null == itemLinkedHashMap.get(title)) {
            return this;
        }

        MenuItem menuItem = mToolbar.getMenu().findItem(itemLinkedHashMap.get(title).getId());
        if (null == menuItem) {
            return this;
        }
        menuItem.setVisible(visibility);
        return this;
    }


    /**
     * 设置 标题栏北京
     *
     * @param backGroundResId
     * @return
     */
    public TitleBarBuilder setBackGround(int backGroundResId) {
        mToolbar.setBackgroundResource(backGroundResId);
        return this;
    }

    /**
     * 设置标题文字宽度
     *
     * @param width dp
     */
    public void setTitleTvWidth(int width) {
        ViewGroup.LayoutParams layoutParams = tvTitle.getLayoutParams();
        layoutParams.width = dp2px(width);
        tvTitle.setLayoutParams(layoutParams);
        tvTitle.invalidate();
    }


    /**
     * 自定义返回键
     *
     * @param listener
     * @return
     */
    public TitleBarBuilder setBackIconClickEvent(final ItemClickListener listener) {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick();
            }
        });
        return this;
    }

    /**
     * 自定义返回键
     *
     * @param description
     * @param iconResId
     * @param listener
     * @return
     */
    public TitleBarBuilder setBackIcon(String description, int iconResId, final ItemClickListener listener) {
        mToolbar.setNavigationIcon(iconResId);
        mToolbar.setNavigationContentDescription(description);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick();
            }
        });
        return this;
    }

    /**
     * 隐藏返回按钮
     *
     * @return
     */
    public TitleBarBuilder hideBackIcon() {
        mToolbar.setNavigationIcon(null);
        return this;
    }

    public TitleBarBuilder addItem(MyMenuItem menuItem) {
        itemLinkedHashMap.put(menuItem.getTitle(), menuItem);
        return this;
    }

    public TitleBarBuilder removeItem(String title) {
        if (!itemLinkedHashMap.containsKey(title)) {
            return this;
        }
        MyMenuItem remove = itemLinkedHashMap.remove(title);
        if (remove.getId() == -1) {
            return this;
        }
        Menu menu = mToolbar.getMenu();
        if (menu == null) {
            return this;
        }
        menu.findItem(remove.getId()).setVisible(false);

        return this;
    }

    public TitleBarBuilder addItem(String title, ItemClickListener listener) {
        MyMenuItem menuItem = new MyMenuItem(title, -1, MyMenuItem.Type.OTHER, listener);
        itemLinkedHashMap.put(menuItem.getTitle(), menuItem);
        return this;
    }

    public TitleBarBuilder addItem(String title, int iconResId, ItemClickListener listener) {
        MyMenuItem menuItem = new MyMenuItem(title, iconResId, MyMenuItem.Type.OTHER, listener);
        itemLinkedHashMap.put(menuItem.getTitle(), menuItem);
        return this;
    }

    public TitleBarBuilder addShareMenuItem(ItemClickListener listener) {
        MyMenuItem menuItem = new MyMenuItem("分享", R.drawable.icon_share, MyMenuItem.Type.SHARE, listener);
        itemLinkedHashMap.put(menuItem.getTitle(), menuItem);
        return this;
    }

    public TitleBarBuilder addMoreMenuItem(ItemClickListener listener) {
        MyMenuItem menuItem = new MyMenuItem("更多", R.drawable.icon_more, MyMenuItem.Type.MORE, listener);
        itemLinkedHashMap.put(menuItem.getTitle(), menuItem);
        return this;
    }

    public TitleBarBuilder addSearchMenuItem(ItemClickListener listener) {
        MyMenuItem menuItem = new MyMenuItem("搜索", R.drawable.icon_search, MyMenuItem.Type.SEARCH, listener);
        itemLinkedHashMap.put(menuItem.getTitle(), menuItem);
        return this;
    }


    private void addMenuItem(Menu menu, int itemResId, MyMenuItem myMenuItem) {
        menu.findItem(itemResId).setVisible(true);
        if (myMenuItem.getIconResId() > 0) {
            menu.findItem(itemResId).setIcon(myMenuItem.getIconResId());
        } else {
            menu.findItem(itemResId).setIcon(0);
        }
        menu.findItem(itemResId).setTitle(myMenuItem.getTitle());
    }


    private boolean setMyMenu(Menu menu) {
        if (null == itemLinkedHashMap || itemLinkedHashMap.isEmpty()) {
            Log.e("TAG" + getClass().getSimpleName(), "menu list is empty");
            return false;
        }

        int menuCount = 0;

        Iterator<Map.Entry<String, MyMenuItem>> iterator = itemLinkedHashMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, MyMenuItem> entry = iterator.next();
            MyMenuItem item = entry.getValue();

            switch (item.getType()) {
                case SEARCH:
                    item.setIconResId(R.drawable.icon_search);
                    break;
                case SHARE:
                    item.setIconResId(R.drawable.icon_share);
                    break;
                case MORE:
                    item.setIconResId(R.drawable.icon_more);
                    break;
                case OTHER:
                    break;
            }
            int itemId;
            switch (menuCount) {
                case 0:
                    itemId = R.id.item1;
                    addMenuItem(menu, R.id.item1, item);
                    break;
                case 1:
                    itemId = R.id.item2;
                    addMenuItem(menu, R.id.item2, item);
                    break;
                case 2:
                    itemId = R.id.item3;
                    addMenuItem(menu, R.id.item3, item);
                    break;
                case 3:
                    itemId = R.id.item4;
                    addMenuItem(menu, R.id.item4, item);
                    break;
                case 4:
                    itemId = R.id.item5;
                    addMenuItem(menu, R.id.item5, item);
                    break;
                default:
                    itemId = -menuCount;
                    menu.add(1, itemId, 0, item.getTitle());
                    Log.e("TAG" + getClass().getSimpleName(), "menu item size is over 5 ");
                    break;
            }

            item.setId(itemId);
            menuCount++;

        }
        return true;
    }


    /**
     * dp转px
     */
    public static int dp2px(float dpVal) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpVal * scale + 0.5f);
    }

    /**
     * sp转px
     */
    public static int sp2px(float spVal) {
        final float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (int) (spVal * fontScale + 0.5f);
    }


}
