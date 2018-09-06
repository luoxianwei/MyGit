package com.yeuyt.mygit.ui.activity.detail_list;

import android.app.Activity;
import android.content.Intent;

import com.yeuyt.mygit.model.entity.UserEntity;
import com.yeuyt.mygit.tools.Constant;
import com.yeuyt.mygit.tools.utils.Utils;

import java.util.List;

public class FollowersListActivity extends UserListActivity {

    public static void launch(String user, Activity src) {
        Intent intent = new Intent(src, FollowersListActivity.class);
        intent.putExtra(Constant.USER_NAME, user);
        src.startActivity(intent);
    }

    @Override
    protected String getToolbarTitle() {
        return "My Followers";
    }
    @Override
    protected void loadMore() {
        presenter.followersUserList(user, curPage);
    }

    @Override
    protected void upRefresh() {
        //这里的作用是防止下拉刷新的时候还可以上拉加载
        adapter.setEnableLoadMore(false);
        curPage = 1;
        sw_layout.setRefreshing(true);
        presenter.followersUserList(user, curPage);
    }


    @Override
    public void loadUsersList(List<UserEntity> userEntities) {
        if (curPage == 1) {
            adapter.setNewData(userEntities);
            adapter.setEnableLoadMore(true);
            sw_layout.setRefreshing(false);
            curPage++;
        } else {
            if(userEntities.size() == 0) {
                Utils.showToastLong("没有数据了");
            } else {
                adapter.addData(userEntities);
                curPage++;
            }
        }

    }

}
