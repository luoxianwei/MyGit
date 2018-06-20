package com.yeuyt.mygit.model.entity;

import com.google.gson.annotations.SerializedName;

public class RepositoryInfo {
    public boolean hasCheckState = false;
    public boolean hasStarred = false;
    public boolean hasWatched = false;

    public int id;
    public String name;
    public String full_name;

    public UserEntity owner;
    @SerializedName("private")
    public boolean privateX;
    public String html_url;
    public String description;
    public boolean fork;
    @SerializedName("stargazers_count")
    public int stars_count;
    public int watchers_count;
    public String language;
    public int forks_count;
    public int open_issues_count;
    public int forks;
    public int open_issues;
    public String created_at;
    public String updated_at;
}
