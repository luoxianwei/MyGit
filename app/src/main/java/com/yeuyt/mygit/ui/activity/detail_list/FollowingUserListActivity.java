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
        presenter.followingUserList(user, curPage);
    }

    @Override
    protected void upRefresh() {
        adapter.setEnableLoadMore(false);
        curPage = 1;
        sw_layout.setRefreshing(true);
        presenter.followingUserList(user, curPage);
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
