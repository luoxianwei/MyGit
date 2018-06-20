package com.yeuyt.mygit.model.entity;

import android.support.annotation.IntDef;
import android.text.TextUtils;

import com.yeuyt.mygit.tools.utils.Utils;


public class GitTree {
    public static final int FILE = 0;
    public static final int DIR = 1;

    @IntDef({FILE, DIR})
    public @interface TreeNodeType {
    }


    public String sha;
    public String url;
    public boolean truncated;


    public static class TreeEntity {
        public String path;
        public String mode;
        public String type;//blob or tree
        public String sha;
        public int size;
        public String html_url;

        @TreeNodeType
        public int getNodeType() {
            if ("blob".equals(type))
                return FILE;
            else return DIR;
        }
        public String getHtml_url(String user, String repo, String branchName) {
            if (TextUtils.isEmpty(html_url)) {
                html_url = Utils.append("https://github.com/", user, "/", repo, "/blob/", branchName, "/", path);
            }
            return html_url;
        }
    }
}
