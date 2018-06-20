package com.yeuyt.mygit.tools.helper;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yeuyt.mygit.di.GitApplication;

public class ImageLoaderHelper {
    public static void loadImage(Context context, int id, ImageView view) {
        Glide.with(context).load(id).into(view);
    }

    public static void loadImage(Context context, String url, ImageView view) {
            Glide.with(context).load(url).into(view);
    }

    public static void loadImage(String url, ImageView view) {
            Glide.with(GitApplication.getContext()).load(url).into(view);
    }
}
