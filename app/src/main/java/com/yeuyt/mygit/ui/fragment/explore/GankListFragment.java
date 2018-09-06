package com.yeuyt.mygit.ui.fragment.explore;

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
import com.yeuyt.mygit.model.entity.RepositoryInfo;
import com.yeuyt.mygit.presenter.contract.ExploreContract;
import com.yeuyt.mygit.tools.utils.LogUtils;
import com.yeuyt.mygit.tools.utils.Utils;
import com.yeuyt.mygit.ui.activity.RepoPageActivity;
import com.yeuyt.mygit.ui.activity.detail_list.BaseListActivity;
import com.yeuyt.mygit.ui.adapter.ListRepoAdapter;

import java.util.List;

public class GankListFragment extends Fragment {

    protected SwipeRefreshLayout sw_layout;
    protected RecyclerView rv;
    protected View rootView;
    protected int curPage = 1;
    protected ListRepoAdapter adapter;

    protected ExploreContract.Presenter presenter;

    public void setPresenter(ExploreContract.Presenter presenter) {
        this.presenter = presenter;
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

        //adapter.setFooterView(footerView);

        //进入页面的时候加载数据
        upRefresh();
    }

    protected void bindRvAdapter() {
        adapter = new ListRepoAdapter(R.layout.item_repo);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                RepositoryInfo repositoryInfo = (RepositoryInfo)adapter.getItem(position);

                RepoPageActivity.launch(getActivity(), repositoryInfo.owner.login, repositoryInfo.name);
            }
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                switch (view.getId()) {
                    case R.id.tv_stars:
                        Utils.showToastLong("tv_stars");
                        break;
                    case R.id.tv_forks:
                        Utils.showToastLong("tv_stars");
                        break;
                    case R.id.tv_watches:
                        Utils.showToastLong("tv_stars");
                        break;
                }
            }
        });
        rv.setAdapter(adapter);
    }

    protected BaseQuickAdapter getAdapter() {
        return adapter;
    }

    public void loadMore() {
        if (presenter != null) {
            presenter.getGankRepositories(curPage);
        }
    }

    public void upRefresh() {
        if (presenter != null) {
            //这里的作用是防止下拉刷新的时候还可以上拉加载
            adapter.setEnableLoadMore(false);
            curPage = 1;
            sw_layout.setRefreshing(true);
            presenter.getGankRepositories(curPage);
        }
    }

    public void loadRepoList(List<RepositoryInfo> repositoryInfos) {
        if(repositoryInfos == null) {
            sw_layout.setRefreshing(false);
            return;
        }
        //==1说明为上拉加载
        if (curPage == 1) {
            adapter.setNewData(repositoryInfos);
            adapter.setEnableLoadMore(true);
            sw_layout.setRefreshing(false);
            curPage++;
        } else {
            if(repositoryInfos.size() == 0) {
                Utils.showToastLong("没有数据了");

            } else {
                adapter.addData(repositoryInfos);
                curPage++;
            }
        }

    }
    public void initData() {
        bindRvAdapter();

        initRefresh();
    }
}
