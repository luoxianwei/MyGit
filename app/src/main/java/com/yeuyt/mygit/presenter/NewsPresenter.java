package com.yeuyt.mygit.presenter;

import android.text.TextUtils;

import com.yeuyt.mygit.model.entity.Event;
import com.yeuyt.mygit.model.net.dataSource.UserDataSource;
import com.yeuyt.mygit.presenter.contract.NewsContract;
import com.yeuyt.mygit.tools.helper.AccountHelper;
import com.yeuyt.mygit.di.GitApplication;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;


public class NewsPresenter extends BasePresenter<NewsContract.View> implements NewsContract.Presenter {
    UserDataSource userDataSource;

    public NewsPresenter(UserDataSource userDataSource) {
        this.userDataSource = userDataSource;
    }

    @Override
    public void getNews(int page) {
        String user = AccountHelper.getLoginUser(GitApplication.getContext());
        if (TextUtils.isEmpty(user)) {
            getView().loadNewsList(new ArrayList<Event>());
            return;
        }
        addDisposable(userDataSource.listNews(user, page)
            .subscribe(new Consumer<List<Event>>() {
                @Override
                public void accept(List<Event> events) throws Exception {
                    getView().loadNewsList(events);
                }
            }));
    }


}
