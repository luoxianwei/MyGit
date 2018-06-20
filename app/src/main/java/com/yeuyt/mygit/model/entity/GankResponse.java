package com.yeuyt.mygit.model.entity;

import java.util.List;

public class GankResponse {

    public boolean error;

    public List<ResultsEntity> results;

    public static class ResultsEntity {
        private String type;
        private String url;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
