package com.yeuyt.mygit.ui.activity.detail_list;

import android.app.Activity;
import android.content.Intent;

import com.yeuyt.mygit.model.entity.UserEntity;
import com.yeuyt.mygit.tools.Constant;
import com.yeuyt.mygit.tools.utils.Utils;

import java.util.List;

public class FollowingUserListActivity extends UserListActivity {

    public static void launch(String user, Activity src) {
        Intent intent = new Intent(src, FollowingUserListActivity.class);
        intent.putExtra(Constant.USER_NAME, user);
        src.startActivity(intent);
    }

    @Override
    protected String getToolbarTitle() {
        return "My Following";
    }
    @Override
    protected void loadMore() {
        presenter.followingUserList(user, curPage++);
    }

    @Override
    protected void upRefresh() {
        //这里的作用是防止下拉刷新的时候还可以上拉加载
        adapter.setEnableLoadMore(false);
        curPage = 1;
        presenter.followingUserList(user, curPage++);
    }

    @Override
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
