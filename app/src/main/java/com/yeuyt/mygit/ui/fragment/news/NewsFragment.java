package com.yeuyt.mygit.ui.fragment.news;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yeuyt.mygit.R;
import com.yeuyt.mygit.di.ComponentHolder;
import com.yeuyt.mygit.model.entity.Event;
import com.yeuyt.mygit.presenter.contract.NewsContract;
import com.yeuyt.mygit.tools.utils.Utils;
import com.yeuyt.mygit.ui.adapter.ListNewsAdapter;
import com.yeuyt.mygit.ui.fragment.LazyFragment;

import java.util.List;

import javax.inject.Inject;

public class NewsFragment extends LazyFragment implements NewsContract.View{
    SwipeRefreshLayout sw_layout;
    RecyclerView rv;
    ListNewsAdapter adapter;

    protected int curPage = 1;
    @Inject
    NewsContract.Presenter presenter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    public void initView() {
        sw_layout = rootView.findViewById(R.id.sw_layout);
        rv = rootView.findViewById(R.id.rv_list);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initDagger();
        bindRvAdapter();
        initRefresh();
    }
    private void bindRvAdapter() {
        adapter = new ListNewsAdapter(R.layout.item_news);
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Utils.showToastLong("默认操作");
            }
        });

        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(adapter);
    }
    private void initRefresh() {
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
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
        adapter.disableLoadMoreIfNotFullPage();
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
        adapter.setEmptyView(view);

        //adapter.setFooterView(footerView);

        if (Utils.isNetworkAvailable(getContext())) {
            sw_layout.setRefreshing(true);
            upRefresh();
        } else
            Utils.showToastLong("没有网络");
    }

    public void loadMore() {
        //这里的作用是防止下拉刷新的时候还可以上拉加载
        adapter.setEnableLoadMore(false);
        curPage = 1;
        presenter.getNews(curPage++);
    }

    public void upRefresh() {
        presenter.getNews(curPage++);
    }

    protected void initDagger() {
        ComponentHolder.newsComponent.inject(this);
        presenter.attachView(this);
    }

    @Override
    public void loadNewsList(List<Event> news) {
        if (curPage == 2) {
            adapter.setNewData(news);
            adapter.setEnableLoadMore(true);
            sw_layout.setRefreshing(false);

        } else {
            if(news.size() == 0) {
                Utils.showToastLong("没有数据了");
                adapter.loadMoreEnd();
            } else {
                adapter.addData(news);
                adapter.loadMoreComplete();
            }
        }
    }
}
