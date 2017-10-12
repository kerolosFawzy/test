package com.massive.popmovie.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by minafaw on 10/12/2017.
 */

public class Reviews implements Serializable {
    private String author;
    private String content;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
