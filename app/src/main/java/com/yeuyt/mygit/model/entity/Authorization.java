package com.yeuyt.mygit.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Authorization {
    public int id;
    public String url;

    public AppBean app;
    public String token;
    public String hashed_token;
    public String token_last_eight;
    public String note;
    public String note_url;
    public String created_at;
    public String updated_at;
    public String fingerprint;
    public String[] scopes;

    public static class AppBean {
        public String name;
        public String url;
        public String client_id;


    }

}