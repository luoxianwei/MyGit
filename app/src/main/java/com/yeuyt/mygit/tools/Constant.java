package com.yeuyt.mygit.tools;

import com.yeuyt.mygit.R;
import com.yeuyt.mygit.tools.utils.Utils;

public class Constant {
    public static final String URL_GITHUB = Utils.getString(R.string.URL_GITHUB);
    public static final String URL_GANK = Utils.getString(R.string.URL_GANK);
    public static final String GITHUB_CLIENT_ID = Utils.getString(R.string.GITHUB_CLIENT_ID);
    public static final String GITHUB_CLIENT_SECRET = Utils.getString(R.string.GITHUB_CLIENT_SECRET);

    public static final String[] SCOPES = {"user", "repo"};

    public static final String NOTE = Utils.getString(R.string.app_name);

    public static final int PER_PAGE = 10;

    public static final String USER_NAME = "USER_NAME";
    public static final String REPO_NAME = "REPO_NAME";
}
