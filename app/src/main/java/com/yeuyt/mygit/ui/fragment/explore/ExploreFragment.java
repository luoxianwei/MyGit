package com.yeuyt.mygit.ui.fragment.explore;


import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.yeuyt.mygit.R;
import com.yeuyt.mygit.di.ComponentHolder;
import com.yeuyt.mygit.model.entity.RepositoryInfo;
import com.yeuyt.mygit.presenter.contract.ExploreContract;
import com.yeuyt.mygit.tools.utils.Utils;
import com.yeuyt.mygit.ui.adapter.ViewPagerAdapter;
import com.yeuyt.mygit.ui.fragment.LazyFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;


public class ExploreFragment extends LazyFragment implements ExploreContract.View{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.collapsing)
    CollapsingToolbarLayout collapsing;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.iv_header)
    ImageView ivHeadr;

    @Inject
    ExploreContract.Presenter presenter;

    GankListFragment gankListFragment;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_explore;
    }

    @Override
    public void initView() {
        setHasOptionsMenu(true);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

    }

    @Override
    public void initData() {
        initDagger();

        setupViewPager();
        tab.setupWithViewPager(viewpager);
    }
    protected void initDagger() {
        ComponentHolder.exploreComponent.inject(this);
        presenter.attachView(this);
    }
    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        gankListFragment = new GankListFragment();
        gankListFragment.setPresenter(presenter);//设置操作必须在initDagger()之后
        adapter.addFragment(Utils.getString(R.string.gank), gankListFragment);

        //adapter.addFragment(Utils.getString(R.string.discover), new GankListFragment());
        //adapter.addFragment(Utils.getString(R.string.discover), new GankListFragment());
        //adapter.addFragment(Utils.getString(R.string.discover), new GankListFragment());
        viewpager.setAdapter(adapter);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    collapsing.setBackgroundColor(getResources().getColor(R.color.light_orange));
                    Glide.with(ExploreFragment.this).load(R.mipmap.train).into(ivHeadr);
                } else if(position == 1) {
                    collapsing.setBackgroundColor(getResources().getColor(R.color.light_orange));
                    Glide.with(ExploreFragment.this).load(R.mipmap.sun).into(ivHeadr);
                } else if(position == 2) {
                    collapsing.setBackgroundColor(getResources().getColor(R.color.light_orange));
                    Glide.with(ExploreFragment.this).load(R.mipmap.sky).into(ivHeadr);
                } else if(position == 3) {
                    collapsing.setBackgroundColor(getResources().getColor(R.color.light_orange));
                    Glide.with(ExploreFragment.this).load(R.mipmap.arsenal).into(ivHeadr);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });
    }

    @Override
    public void loadGankData(List<RepositoryInfo> repositoryInfos) {
        gankListFragment.loadRepoList(repositoryInfos);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
        }
    }
}
