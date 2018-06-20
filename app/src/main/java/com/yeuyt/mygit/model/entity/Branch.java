package com.yeuyt.mygit.model.entity;

public class Branch {

    private String name;

    private CommitEntity commit;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CommitEntity getCommit() {
        return commit;
    }

    public void setCommit(CommitEntity commit) {
        this.commit = commit;
    }

    public static class CommitEntity {
        private String sha;
        private String url;

        public String getSha() {
            return sha;
        }

        public void setSha(String sha) {
            this.sha = sha;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    @Override
    public String toString() {
        return "Branch{" +
                "name='" + name + '\'' +
                ", commit=" + commit +
                '}';
    }

}
