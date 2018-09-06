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
    public void initData() {
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

        rv.setAdapter(adapter);
    }
    private void initRefresh() {
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
        adapter.setEmptyView(view);

        upRefresh();
    }

    public void loadMore() {
        presenter.getNews(curPage);
    }

    public void upRefresh() {
        adapter.setEnableLoadMore(false);
        curPage = 1;
        sw_layout.setRefreshing(true);
        presenter.getNews(curPage);
    }

    protected void initDagger() {
        ComponentHolder.newsComponent.inject(this);
        presenter.attachView(this);
    }

    @Override
    public void loadNewsList(List<Event> news) {
        if(news == null) {
            sw_layout.setRefreshing(false);
            return;
        }
        if (curPage == 1) {
            adapter.setNewData(news);
            adapter.setEnableLoadMore(true);
            sw_layout.setRefreshing(false);
            curPage++;
        } else {
            if(news.size() == 0) {
                Utils.showToastLong("没有数据了");
                ;
            } else {
                adapter.addData(news);
                curPage++;
            }
        }
    }
}
