package com.massive.popmovie.model;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class reviewsResponse {
    @SerializedName("id")
    private long id;
    @SerializedName("results")
    private ArrayList<Reviews> results;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ArrayList<Reviews> getResults() {
        return results;
    }

    public void setResults(ArrayList<Reviews> results) {
        this.results = results;
    }
}
