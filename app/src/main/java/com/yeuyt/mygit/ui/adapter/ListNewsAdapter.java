package com.yeuyt.mygit.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yeuyt.mygit.R;
import com.yeuyt.mygit.model.entity.Event;
import com.yeuyt.mygit.tools.utils.DateUtils;
import com.yeuyt.mygit.tools.helper.ImageLoaderHelper;

import java.util.List;

public class ListNewsAdapter extends BaseQuickAdapter<Event, BaseViewHolder> {

    public ListNewsAdapter(int layoutResId, @Nullable List<Event> data) {
        super(layoutResId, data);
    }

    public ListNewsAdapter(@Nullable List<Event> data) {
        super(data);
    }

    public ListNewsAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, Event item) {

        helper.setText(R.id.tv_time_ago, DateUtils.timeAgo(item.created_at));
        helper.setText(R.id.tv_type, item.type);

        if(item.actor != null) {
            helper.setText(R.id.tv_actor, item.actor.login);
            ImageLoaderHelper.loadImage(item.actor.avatar_url, (ImageView) helper.getView(R.id.iv_actor));
        }
        if (item.repo != null) {
            helper.setText(R.id.tv_repo, item.repo.name);
        }
        if (item.payload != null && item.payload.comment != null) {
            helper.setText(R.id.tv_desc, item.payload.comment.body);
        }

    }
}
