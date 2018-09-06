package com.yeuyt.mygit.ui.activity.detail_list;


import android.app.Activity;
import android.content.Intent;

import com.yeuyt.mygit.model.entity.RepositoryInfo;
import com.yeuyt.mygit.tools.Constant;
import com.yeuyt.mygit.tools.utils.Utils;

import java.util.List;

public class StarsRepoListActivity extends RepoListActivity {

    public static void launch(String user, Activity src) {
        Intent intent = new Intent(src, StarsRepoListActivity.class);
        intent.putExtra(Constant.USER_NAME, user);
        src.startActivity(intent);
    }

    @Override
    protected String getToolbarTitle() {
        return "Stars";
    }
    @Override
    protected void loadMore() {
        presenter.staredRepoList(user, curPage);
    }

    @Override
    protected void upRefresh() {
        //这里的作用是防止下拉刷新的时候还可以上拉加载
        adapter.setEnableLoadMore(false);
        curPage = 1;
        sw_layout.setRefreshing(true);
        presenter.staredRepoList(user, curPage);
    }

    @Override
    public void loadRepoList(List<RepositoryInfo> repositoryInfos) {
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
}
