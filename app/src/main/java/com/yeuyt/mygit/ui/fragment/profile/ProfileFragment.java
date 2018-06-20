package com.yeuyt.mygit.ui.fragment.profile;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yeuyt.mygit.R;
import com.yeuyt.mygit.model.entity.UserInfo;
import com.yeuyt.mygit.tools.helper.AccountHelper;
import com.yeuyt.mygit.tools.helper.ImageLoaderHelper;
import com.yeuyt.mygit.tools.utils.Utils;
import com.yeuyt.mygit.ui.activity.detail_list.FollowersListActivity;
import com.yeuyt.mygit.ui.activity.detail_list.FollowingUserListActivity;
import com.yeuyt.mygit.ui.activity.detail_list.StarsRepoListActivity;
import com.yeuyt.mygit.ui.activity.detail_list.UserRepoListActivity;
import com.yeuyt.mygit.ui.activity.detail_list.WatchingRepoListActivity;
import com.yeuyt.mygit.ui.fragment.LazyFragment;
import com.yeuyt.mygit.ui.fragment.login.LoginFragment;


import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends LazyFragment implements LoginFragment.LoginCallBack{

    @BindView(R.id.personal)
    CardView personal;
    @BindView(R.id.item_stars)
    CardView itemStars;
    @BindView(R.id.item_watching)
    CardView itemWatching;
    @BindView(R.id.item_following)
    CardView itemFollowing;
    @BindView(R.id.item_followers)
    CardView itemFollowers;
    @BindView(R.id.item_repo)
    CardView itemRepo;
    @BindView(R.id.item_setting)
    CardView itemSetting;
    @BindView(R.id.item_sign_out)
    TextView itemSignOut;
    @BindView(R.id.avatar)
    CircleImageView circleImageView;
    @BindView(R.id.name)
    TextView name;
    UserInfo info;

    LoginFragment loginFragment;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_profile;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        if(AccountHelper.isLogin(getViewContext())) {
            itemSignOut.setText("Sign Out");
            info = AccountHelper.getUserInfo(getViewContext());
            ImageLoaderHelper.loadImage(getViewContext(), info.avatar_url, circleImageView);
            name.setText(info.login);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        loginFragment = new LoginFragment();
        loginFragment.setLoginCallBack(this);
        return rootView;
    }

    @OnClick({R.id.personal, R.id.item_stars, R.id.item_watching,
            R.id.item_following, R.id.item_followers,
            R.id.item_repo, R.id.item_setting, R.id.item_sign_out})
    public void onViewClicked(View view) {
        if(view.getId() != R.id.item_setting && !AccountHelper.isLogin(getViewContext())) {
            showDialog();
            return;
        } else if(info == null) {
            initData(null);
        }


        switch (view.getId()) {
            case R.id.personal:
                if(!AccountHelper.isLogin(getContext())) {
                    showDialog();
                }
                break;
            case R.id.item_stars:
                StarsRepoListActivity.launch(info.login, getActivity());
                break;
            case R.id.item_watching:
                WatchingRepoListActivity.launch(info.login, getActivity());
                break;
            case R.id.item_following:
                FollowingUserListActivity.launch(info.login, getActivity());
                break;
            case R.id.item_followers:
                FollowersListActivity.launch(info.login, getActivity());
                break;
            case R.id.item_repo:
                //info.name是昵称，info.login是登陆名
                UserRepoListActivity.launch(info.login, getActivity());
                break;
            case R.id.item_setting:
                break;
            case R.id.item_sign_out:
                if (AccountHelper.isLogin(getViewContext())) {
                    AccountHelper.removeUserInfo(getViewContext());
                    Utils.showToastLong("sign out sucesss");
                    itemSignOut.setText("Sign In");
                    name.setText("Login in");
                    ImageLoaderHelper.loadImage(getViewContext(), R.mipmap.ic_launcher, circleImageView);
                } else {
                    showDialog();
                }
                break;
        }
    }

    private void showDialog() {
        if (loginFragment == null) {
            loginFragment = new LoginFragment();
        }
        loginFragment.show(getFragmentManager(), "");
    }

    @Override
    public void onSuccessLogin() {
        itemSignOut.setText("Sign Out");
        info = AccountHelper.getUserInfo(getViewContext());
        ImageLoaderHelper.loadImage(getViewContext(), info.avatar_url, circleImageView);
        name.setText(info.login);
        loginFragment.dismiss();
    }

    @Override
    public void onCancelLogin() {
        loginFragment.dismiss();
    }
}