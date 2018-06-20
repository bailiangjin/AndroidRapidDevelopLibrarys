package com.bailiangjin.uilibrary.fragment.listfragment;

import android.support.v4.widget.SwipeRefreshLayout;

import com.bailiangjin.uilibrary.R;
import com.bailiangjin.uilibrary.recyclerview.adapter.RVMultiTypeBaseAdapter;
import com.bailiangjin.uilibrary.recyclerview.interfaze.ILoadMoreCallback;
import com.bailiangjin.uilibrary.recyclerview.interfaze.IRefreshCallback;
import com.bailiangjin.uilibrary.recyclerview.wrapper.LinearRVLoadMoreWrapper;
import com.zhy.adapter.recyclerview.wrapper.LoadMoreWrapper;


/**
 * Created by bailiangjin on 2017/3/22.
 */

public abstract class PullToRefreshAndLoadMoreFragment extends ListFragment {

    SwipeRefreshLayout swipeRefreshLayout;

    protected LinearRVLoadMoreWrapper loadMoreWrapper;

    private boolean isDisableRefresh = false;


    private boolean hasMoreData = true;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_with_refresh_load_more_rv_list;
    }


    @Override
    protected void beforeViewBind() {

    }

    @Override
    protected void initView() {
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        super.initView();
    }

    @Override
    protected void initRefresh() {
        super.initRefresh();
        if(null!=getColorSchemeResources()&&getColorSchemeResources().length>0){
            swipeRefreshLayout.setColorSchemeResources(getColorSchemeResources());
        }else {
            swipeRefreshLayout.setColorSchemeResources(R.color.color_scheme_2_1,
                    R.color.color_scheme_2_2, R.color.color_scheme_2_3,
                    R.color.color_scheme_2_4);
        }

        isDisableRefresh = isDisableRefresh();

        if (isDisableRefresh) {
            return;
        }


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉刷新
                initOrRefreshData(new IRefreshCallback() {

                    @Override
                    public void onStart() {
                        // do nothing
                    }

                    @Override
                    public void onSuccess() {
                        hideRefreshProgressBar();
                        notifyAdapterDataChanged();
                    }

                    @Override
                    public void onFailed() {
                        hideRefreshProgressBar();
                        shortToast("刷新失败");
                    }
                });
            }
        });
    }


    @Override
    protected void initLoadMore() {
        super.initLoadMore();

        if(!isDisableLoadMore()){
            loadMoreWrapper = new LinearRVLoadMoreWrapper(listRvAdapter, recyclerView);
            loadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {

                    if (hasMoreData) {
                        loadMoreData(new ILoadMoreCallback() {
                            @Override
                            public void onLoadResult(boolean isHasMoreData) {
                                setHasMoreData(isHasMoreData);
                            }
                        });
                    } else {
                        setHasMoreData(false);
                    }
                }
            });
            realAdapter = loadMoreWrapper;
            recyclerView.setAdapter(realAdapter);
        }

    }

    public void setHasMoreData(boolean isHasMoreData) {
        if (isHasMoreData) {
            notifyAdapterDataChanged();
        }
        this.hasMoreData = isHasMoreData;
        this.loadMoreWrapper.setHasMoreData(isHasMoreData);

    }

    @Override
    protected void setAdapter(RVMultiTypeBaseAdapter adapter) {
        super.setAdapter(adapter);
        initLoadMore();
    }

    protected int[] getColorSchemeResources(){
        return null;
    }

    protected void hideRefreshProgressBar() {
        swipeRefreshLayout.setRefreshing(false);
    }

    protected void disableRefresh() {
        isDisableRefresh = true;
        swipeRefreshLayout.setEnabled(false);
    }

    protected void enableRefresh() {
        isDisableRefresh = false;
        swipeRefreshLayout.setEnabled(true);
    }


    protected abstract boolean isDisableLoadMore();


    protected abstract boolean isDisableRefresh();


    public abstract void initOrRefreshData(IRefreshCallback refreshCallback);

    protected abstract void loadMoreData(ILoadMoreCallback loadMoreCallback);


}
