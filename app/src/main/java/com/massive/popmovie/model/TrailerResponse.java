package com.massive.popmovie.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;


public class TrailerResponse  {
    @SerializedName("id")
    private long id;
    @SerializedName("results")
    private ArrayList<Trailer> results;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ArrayList<Trailer> getResults() {
        return results;
    }

    public void setResults(ArrayList<Trailer> results) {
        this.results = results;
    }
}
