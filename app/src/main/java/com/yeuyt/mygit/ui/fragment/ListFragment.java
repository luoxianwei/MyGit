package com.yeuyt.mygit.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yeuyt.mygit.R;
import com.yeuyt.mygit.presenter.contract.SearchListContract;
import com.yeuyt.mygit.tools.utils.Utils;


public abstract class ListFragment extends Fragment {
    protected SwipeRefreshLayout sw_layout;
    protected RecyclerView rv;
    protected View rootView;

    protected String keyword;

    protected SearchListContract.Presenter presenter;

    public void setPresenter(SearchListContract.Presenter presenter) {
        this.presenter = presenter;
        upRefresh();
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_list, container, false);
        sw_layout = rootView.findViewById(R.id.sw_layout);
        rv = rootView.findViewById(R.id.rv_list);
        initData();
        return rootView;
    }
    protected abstract void bindRvAdapter();
    protected abstract BaseQuickAdapter getAdapter();

    public abstract void loadMore();
    public abstract void upRefresh();


    protected  void initRefresh() {
        getAdapter().setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (Utils.isNetworkAvailable(getContext()))
                    loadMore();
                else
                    Utils.showToastLong("没有网络");
            }
        }, rv);
        //默认第一次加载会进入回调，如果不需要可以配置
        //先setOnLoadMoreListener再配置
        getAdapter().disableLoadMoreIfNotFullPage();
        sw_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                upRefresh();
            }
        });

        View view = LayoutInflater.from(getContext()).inflate(R.layout.empty_view, null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isNetworkAvailable(getContext()))
                    upRefresh();
                else
                    Utils.showToastLong("没有网络");
            }
        });
        getAdapter().setEmptyView(view);

        //adapter.setFooterView(footerView);

        if (Utils.isNetworkAvailable(getContext())) {
            sw_layout.setRefreshing(true);
            upRefresh();
        } else
            Utils.showToastLong("没有网络");
    }

    public void initData() {
        bindRvAdapter();

        initRefresh();
    }

}
