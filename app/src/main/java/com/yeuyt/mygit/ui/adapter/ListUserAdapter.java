package com.yeuyt.mygit.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yeuyt.mygit.R;
import com.yeuyt.mygit.model.entity.UserEntity;
import com.yeuyt.mygit.tools.helper.ImageLoaderHelper;
import com.yeuyt.mygit.tools.utils.LogUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListUserAdapter extends BaseQuickAdapter<UserEntity, BaseViewHolder> {


    public ListUserAdapter(int layoutResId, @Nullable List<UserEntity> data) {
        super(layoutResId, data);
    }

    public ListUserAdapter(@Nullable List<UserEntity> data) {
        super(data);
    }

    public ListUserAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserEntity item) {
        LogUtils.i(item.toString());
        helper.addOnClickListener(R.id.tv_follow);

        helper.setText(R.id.tv_name, item.login);
        helper.setText(R.id.tv_desc, item.bio);
        ImageLoaderHelper.loadImage(item.avatar_url, (CircleImageView) helper.getView(R.id.iv_avatar));

    }
}

