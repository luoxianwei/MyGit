package com.yeuyt.mygit.model.entity;

import java.util.List;

public class SearchResult<T> {

    public int total_count;

    public List<T> items;

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "total_count=" + total_count +
                ", items=" + items +
                '}';
    }
}
