package com.yeuyt.mygit.di;

import android.content.Context;

import com.yeuyt.mygit.di.component.AppComponent;
import com.yeuyt.mygit.di.component.ExploreComponent;
import com.yeuyt.mygit.di.component.ListItemComponent;
import com.yeuyt.mygit.di.component.LoginComponent;
import com.yeuyt.mygit.di.component.NewsComponent;
import com.yeuyt.mygit.di.component.RepoPageComponent;
import com.yeuyt.mygit.di.component.SearchComponent;


public class ComponentHolder {
    public static Context context;
    public static AppComponent appComponent;
    public static LoginComponent loginComponent;
    public static ListItemComponent listItemComponent;
    public static SearchComponent searchComponent;
    public static NewsComponent newsComponent;
    public static ExploreComponent exploreComponent;
    public static RepoPageComponent repoPageComponent;
}
