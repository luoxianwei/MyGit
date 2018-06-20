package com.yeuyt.mygit.model.entity;

import com.google.gson.annotations.SerializedName;

public class Event {
    public String id;
    public String type;

    @SerializedName("public")
    public boolean publicX;

    public String created_at;

    public ActorEntity actor;

    public ActorEntity org;

    public RepoEntity repo;

    public PayloadEntity payload;

    public static class ActorEntity {
        public int id;
        public String login;
        public String display_login;
        public String avatar_url;


    }

    public static class RepoEntity {
        public int id;
        public String name;

    }

    public static class PayloadEntity {
        public String action;
        public String description;
        public RepositoryInfo repository;
        public UserInfo sender;
        public int number;
        public PullRequest pull_request;

        @SerializedName("public")
        public boolean is_public;
        public UserInfo org;
        public String created_at;
        public Issue issue;
        public CommitComment comment;
        public Release release;
        public Team team;
        public long push_id;
        public int size;
        public int distinct_size;
        public String ref;
        public String head;
        public String before;
        public RepositoryInfo forkee;
        public UserInfo member;

        public static class PullRequest {
            public int number;
            public String title;
        }

        public static class Issue {
            public int number;
            public String title;
        }

        public static class CommitComment {
            public String body;
        }

        public static class Release {
            public String body;
        }

        public static class Team {
            public String name;
        }
    }
}
