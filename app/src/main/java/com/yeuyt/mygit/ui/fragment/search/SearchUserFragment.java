package com.yeuyt.mygit.ui.fragment.search;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yeuyt.mygit.R;
import com.yeuyt.mygit.model.entity.UserEntity;
import com.yeuyt.mygit.tools.utils.Utils;
import com.yeuyt.mygit.ui.adapter.ListUserAdapter;
import com.yeuyt.mygit.ui.fragment.ListFragment;

import java.util.List;

public class SearchUserFragment extends ListFragment {
    protected int curPage = 1;
    protected ListUserAdapter adapter;


    protected void bindRvAdapter() {

        adapter = new ListUserAdapter(R.layout.item_user);
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
                    case R.id.tv_follow:
                        Utils.showToastLong("tv_follow");
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
            presenter.searchUser(keyword, curPage++);
        }
    }

    @Override
    public void upRefresh() {
        if (presenter != null)
            presenter.searchUser(keyword, curPage++);
        else
            sw_layout.setRefreshing(false);
    }



    public void loadUsersList(List<UserEntity> userEntities) {
        if (curPage == 2) {
            adapter.setNewData(userEntities);
            adapter.setEnableLoadMore(true);
            sw_layout.setRefreshing(false);

        } else {
            if(userEntities.size() == 0) {
                Utils.showToastLong("没有数据了");
                adapter.loadMoreEnd();
            } else {
                adapter.addData(userEntities);
                adapter.loadMoreComplete();
            }
        }
    }
}
