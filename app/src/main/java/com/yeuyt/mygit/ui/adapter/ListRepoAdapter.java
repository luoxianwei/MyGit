package com.yeuyt.mygit.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yeuyt.mygit.R;
import com.yeuyt.mygit.model.entity.RepositoryInfo;
import com.yeuyt.mygit.tools.helper.ImageLoaderHelper;

import java.util.List;

public class ListRepoAdapter extends BaseQuickAdapter<RepositoryInfo, BaseViewHolder> {

    public ListRepoAdapter(int layoutResId, @Nullable List<RepositoryInfo> data) {
        super(layoutResId, data);
    }

    public ListRepoAdapter(@Nullable List<RepositoryInfo> data) {
        super(data);
    }

    public ListRepoAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, RepositoryInfo item) {
        helper.addOnClickListener(R.id.tv_stars)
                .addOnClickListener(R.id.tv_forks)
                .addOnClickListener(R.id.tv_watches);

        helper.setText(R.id.tv_repo, item.full_name);
        helper.setText(R.id.tv_desc, item.description);
        helper.setText(R.id.tv_language, item.language);
        helper.setText(R.id.tv_stars, item.stars_count+"");
        helper.setText(R.id.tv_forks, item.forks_count+"");
        helper.setText(R.id.tv_watches, item.watchers_count+"");

        if(item.owner == null)
            return;
        ImageLoaderHelper.loadImage(item.owner.avatar_url, (ImageView) helper.getView(R.id.iv_icon));






    }
}
