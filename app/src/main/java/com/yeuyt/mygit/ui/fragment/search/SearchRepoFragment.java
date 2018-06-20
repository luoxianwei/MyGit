package com.yeuyt.mygit.ui.fragment.search;


import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yeuyt.mygit.R;
import com.yeuyt.mygit.model.entity.RepositoryInfo;
import com.yeuyt.mygit.tools.utils.LogUtils;
import com.yeuyt.mygit.tools.utils.Utils;
import com.yeuyt.mygit.ui.adapter.ListRepoAdapter;
import com.yeuyt.mygit.ui.fragment.ListFragment;

import java.util.List;

public class SearchRepoFragment extends ListFragment {
    protected int curPage = 1;
    protected ListRepoAdapter adapter;


    @Override
    protected void bindRvAdapter() {
        adapter = new ListRepoAdapter(R.layout.item_repo);
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Utils.showToastLong("默认操作");
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
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(adapter);
    }

    @Override
    protected BaseQuickAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void loadMore() {
        if (presenter != null) {
            //这里的作用是防止下拉刷新的时候还可以上拉加载
            adapter.setEnableLoadMore(false);
            curPage = 1;
            presenter.searchRepository(keyword, curPage++);
        }
    }

    @Override
    public void upRefresh() {
        if (presenter != null)
            presenter.searchRepository(keyword, curPage++);
        else
            sw_layout.setRefreshing(false);
    }

    public void loadRepoList(List<RepositoryInfo> repositoryInfos) {
        LogUtils.i("bbbbbbbbbbbbbb");
        //==2说明为上拉加载
        if (curPage == 2) {
            adapter.setNewData(repositoryInfos);
            adapter.setEnableLoadMore(true);
            sw_layout.setRefreshing(false);

        } else {
            if(repositoryInfos.size() == 0) {
                Utils.showToastLong("没有数据了");
                adapter.loadMoreEnd();
            } else {
                adapter.addData(repositoryInfos);
                adapter.loadMoreComplete();
            }
        }

    }
}
