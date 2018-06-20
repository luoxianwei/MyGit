package com.yeuyt.mygit.model.entity;

public class UserEntity {
    public String login;
    public int id;
    public String avatar_url;
    public String html_url;
    public String type;
    public String bio;

    public boolean hasCheck = false;
    public boolean isFollowing = false;

    @Override
    public String toString() {
        return "UserEntity{" +
                "login='" + login + '\'' +
                ", id=" + id +
                ", avatar_url='" + avatar_url + '\'' +
                ", html_url='" + html_url + '\'' +
                ", type='" + type + '\'' +
                ", hasCheck=" + hasCheck +
                ", bio='" + bio + '\'' +
                ", isFollowing=" + isFollowing +
                '}';
    }
}
