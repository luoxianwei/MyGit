package com.yeuyt.mygit.ui.activity;

import android.os.Bundle;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.yeuyt.mygit.R;
import com.yeuyt.mygit.tools.utils.Utils;
import com.yeuyt.mygit.ui.adapter.ViewPagerAdapter;
import com.yeuyt.mygit.ui.fragment.explore.ExploreFragment;
import com.yeuyt.mygit.ui.fragment.news.NewsFragment;
import com.yeuyt.mygit.ui.fragment.profile.ProfileFragment;
import com.yeuyt.mygit.ui.fragment.search.SearchFragment;


public class HomeActivity extends BaseActivity{
    AHBottomNavigationViewPager viewPager;
    AHBottomNavigation navigation;
    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public void initView() {
        viewPager = findViewById(R.id.view_pager);
        navigation = findViewById(R.id.navigation_bar);
    }

    @Override
    public void initData() {
        initViewPager();
        initButtonBar();

    }

    private void initViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(Utils.getString(R.string.explore), new ExploreFragment());
        adapter.addFragment(Utils.getString(R.string.news), new NewsFragment());
        adapter.addFragment(Utils.getString(R.string.search), new SearchFragment());
        adapter.addFragment(Utils.getString(R.string.profile), new ProfileFragment());
        viewPager.setAdapter(adapter);
    }

    private void initButtonBar() {
        AHBottomNavigationItem search = new AHBottomNavigationItem(R.string.search, R.mipmap.ic_search_white, R.color.blue);
        AHBottomNavigationItem profile = new AHBottomNavigationItem(R.string.profile, R.mipmap.ic_personal_page_white, R.color.brown);
        AHBottomNavigationItem explore = new AHBottomNavigationItem(R.string.explore, R.mipmap.ic_explore_white, R.color.orange);
        AHBottomNavigationItem news = new AHBottomNavigationItem(R.string.news, R.mipmap.ic_notifications_white, R.color.green);
        navigation.addItem(explore);
        navigation.addItem(news);
        navigation.addItem(search);
        navigation.addItem(profile);
        navigation.setDefaultBackgroundResource(R.color.black);
        navigation.setColored(true);
        navigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                viewPager.setCurrentItem(position, true);
                return true;
            }
        });
    }
}
