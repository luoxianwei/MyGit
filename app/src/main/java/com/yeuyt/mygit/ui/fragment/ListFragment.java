package com.yeuyt.mygit.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
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
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //当前状态为停止滑动状态SCROLL_STATE_IDLE时
                if(newState == RecyclerView.SCROLL_STATE_IDLE) {
                    LinearLayoutManager manager = (LinearLayoutManager)rv.getLayoutManager();
                    int lastPosition = manager.findLastVisibleItemPosition();
                    if (lastPosition == -1) return;//lastPosition为-1说明没有数据,则没有下拉加载
                    //判断界面显示的最后item的position是否等于itemCount总数-1也就是最后一个item的position
                    //如果相等则说明已经滑动到最后了
                    if(lastPosition == recyclerView.getLayoutManager().getItemCount()-1){
                        loadMore();
                    }
                }

            }
        });
        sw_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                upRefresh();
            }
        });

        View view = LayoutInflater.from(getContext()).inflate(R.layout.empty_view, null);
        getAdapter().setEmptyView(view);

        upRefresh();
    }

    public void initData() {
        bindRvAdapter();

        initRefresh();
    }

}
