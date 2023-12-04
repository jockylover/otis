package com.example.myapplication.data;

import java.io.Serializable;

public class Book implements Serializable {
    private int coverResourceId;
    private String title;

    public Book(int coverResourceId, String title) {
        this.coverResourceId = coverResourceId;
        this.title = title;
    }

    public int getCoverResourceId() {
        return coverResourceId;
    }

    public String getTitle() {
        return title;
    }

    public void setKey(String key) {
        this.title = key;
    }
}
