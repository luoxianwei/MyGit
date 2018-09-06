package com.yeuyt.mygit.ui.fragment.search;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.yeuyt.mygit.R;
import com.yeuyt.mygit.di.ComponentHolder;
import com.yeuyt.mygit.model.entity.RepositoryInfo;
import com.yeuyt.mygit.model.entity.SearchResult;
import com.yeuyt.mygit.model.entity.UserEntity;
import com.yeuyt.mygit.presenter.contract.SearchListContract;
import com.yeuyt.mygit.tools.utils.LogUtils;
import com.yeuyt.mygit.tools.utils.Utils;
import com.yeuyt.mygit.ui.adapter.ViewPagerAdapter;
import com.yeuyt.mygit.ui.fragment.LazyFragment;

import javax.inject.Inject;

import butterknife.BindView;

public class SearchFragment extends LazyFragment implements SearchListContract.View{

    @Inject
    SearchListContract.Presenter presenter;

    @BindView(R.id.search_tab)
    TabLayout searchTab;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    SearchView searchView;
    SearchRepoFragment searchRepoFragment;
    SearchUserFragment searchUserFragment;
    int currentFragment = 0;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_search;
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
        searchTab.setupWithViewPager(viewPager);

    }
    protected void initDagger() {
        ComponentHolder.searchComponent.inject(this);
        presenter.attachView(this);
    }
    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        searchRepoFragment = new SearchRepoFragment();
        searchRepoFragment.setPresenter(presenter);

        searchUserFragment = new SearchUserFragment();
        searchUserFragment.setPresenter(presenter);
        adapter.addFragment("Repo", searchRepoFragment);
        adapter.addFragment("User", searchUserFragment);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                currentFragment = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search, menu);
        initSearchView(menu);
    }

    private void initSearchView(Menu menu) {
        MenuItem item =  menu.findItem(R.id.search);
        searchView = (SearchView)item.getActionView();
        searchView.setQueryHint(Utils.getString(R.string.search));
        //searchView.setSubmitButtonEnabled(true);
        searchView.setIconified(false);
        searchView.onActionViewExpanded();
        searchView.setIconifiedByDefault(false);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
           @Override
           public boolean onQueryTextSubmit(String query) {
               switch (currentFragment) {
                   case 0:
                       searchRepoFragment.setKeyword(query);
                       searchRepoFragment.upRefresh();
                       break;
                   case 1:
                       searchUserFragment.setKeyword(query);
                       searchUserFragment.upRefresh();
                       break;
               }
               return false;
           }

           @Override
           public boolean onQueryTextChange(String newText) {
               if (TextUtils.isEmpty(newText)) {
                   switch (currentFragment) {
                       case 0:
                           searchRepoFragment.setKeyword("");
                           break;
                       case 1:
                           searchUserFragment.setKeyword("");
                           break;
                   }
               }
               return false;
           }
       });
    }

    @Override
    public void loadUsersList(SearchResult<UserEntity> userEntities) {
        if (userEntities == null) {
            searchUserFragment.loadUsersList(null);
        } else {
            searchUserFragment.loadUsersList(userEntities.items);
        }
    }

    @Override
    public void loadRepositoriesList(SearchResult<RepositoryInfo> repoEntities) {
        if (repoEntities == null) {
            searchRepoFragment.loadRepoList(null);
        } else {
            searchRepoFragment.loadRepoList(repoEntities.items);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
        }

    }
}
