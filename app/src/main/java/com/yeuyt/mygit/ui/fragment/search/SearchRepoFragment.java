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
        rv.setAdapter(adapter);
    }

    @Override
    protected BaseQuickAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void loadMore() {
        if (presenter != null) {
            presenter.searchRepository(keyword, curPage);
        }
    }

    @Override
    public void upRefresh() {
        if (presenter != null) {
            adapter.setEnableLoadMore(false);
            curPage = 1;
            sw_layout.setRefreshing(true);
            presenter.searchRepository(keyword, curPage);
        }

    }

    public void loadRepoList(List<RepositoryInfo> repositoryInfos) {
        if(repositoryInfos == null) {
            sw_layout.setRefreshing(false);
            return;
        }
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
}
