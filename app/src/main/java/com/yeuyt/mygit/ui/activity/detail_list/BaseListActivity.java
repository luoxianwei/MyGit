package com.yeuyt.mygit.ui.activity.detail_list;


import android.os.Bundle;

import android.support.v4.widget.SwipeRefreshLayout;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yeuyt.mygit.R;

import com.yeuyt.mygit.tools.utils.Utils;
import com.yeuyt.mygit.ui.activity.BaseActivity;

public abstract class BaseListActivity extends BaseActivity {

    protected Toolbar toolbar;
    protected RecyclerView rv;

    protected View footerView;
    //头和尾布局暂时还没使用
    protected View headerView;

    protected SwipeRefreshLayout sw_layout;
    protected TextView tv_footer_result;
    protected ProgressBar footer_progressBar;

    protected int heightHeader;
    protected int heightFooter;
    @Override
    public int getLayoutId() {
        return R.layout.activity_list;
    }

    @Override
    public void initView() {
        toolbar = findViewById(R.id.toolbar);
        rv = findViewById(R.id.rv_list_repo);

        sw_layout = findViewById(R.id.sw_layout);
        sw_layout.setProgressViewOffset(false, -100, 200);
        sw_layout.setColorSchemeResources(R.color.blue, R.color.green, R.color.red);

        setSupportActionBar(toolbar);
        toolbar.setTitle(getToolbarTitle());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected abstract String getToolbarTitle();
    protected abstract void initDagger();
    protected abstract void getInfoIntent();
    protected abstract void bindRvAdapter();
    protected abstract BaseQuickAdapter getAdapter();

    protected abstract void loadMore();
    protected abstract void upRefresh();


    protected void initHeaderView() {
        headerView = LayoutInflater.from(getViewContext()).inflate(R.layout.item_simple_header, null);
        headerView.measure(0, 0);
        heightHeader = headerView.getMeasuredHeight();
        headerView.setPadding(0, -heightHeader, 0, 0);
    }

    protected void initFooterView() {
        footerView = LayoutInflater.from(getViewContext()).inflate(R.layout.item_simple_footer, null);
        footerView.measure(0, 0);
        heightFooter = footerView.getMeasuredHeight();
        footerView.setPadding(0, -heightFooter, 0, 0);

        tv_footer_result = footerView.findViewById(R.id.tv_footer_result);
        footer_progressBar = footerView.findViewById(R.id.footer_progressBar);
    }

    protected  void initRefresh() {
        getAdapter().setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (Utils.isNetworkAvailable(getViewContext()))
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

        View view = LayoutInflater.from(getViewContext()).inflate(R.layout.empty_view, null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isNetworkAvailable(getViewContext()))
                    upRefresh();
                else
                    Utils.showToastLong("没有网络");
            }
        });
        getAdapter().setEmptyView(view);

        //adapter.setFooterView(footerView);

        if (Utils.isNetworkAvailable(getViewContext())) {
            sw_layout.setRefreshing(true);
            upRefresh();
        } else
            Utils.showToastLong("没有网络");
    }
    @Override
    public void initData(Bundle savedInstanceState) {
        getInfoIntent();
        initDagger();

        initHeaderView();
        initFooterView();
        bindRvAdapter();

        initRefresh();
    }


}
