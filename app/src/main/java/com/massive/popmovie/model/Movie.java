package com.massive.popmovie.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.io.Serializable;


public class Movie extends BaseObservable implements Serializable {
    private String poster_path;
    private String overview;
    private String release_date;
    private long id;
    private String original_title;
    private String title;
    private String backdrop_path;
    private double vote_average;
    private Movie movies;

    @Bindable
    public String getPoster_path() {
        return poster_path;
    }

    @Bindable
    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    @Bindable
    public String getOverview() {
        return overview;
    }

    @Bindable
    public void setOverview(String overview) {
        this.overview = overview;
    }

    @Bindable
    public String getRelease_date() {
        return release_date;
    }

    @Bindable
    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    @Bindable
    public long getId() {
        return id;
    }

    @Bindable
    public void setId(long id) {
        this.id = id;
    }

    @Bindable
    public Movie getMovies() {
        return movies;
    }

    @Bindable
    public void setMovies(Movie movies) {
        this.movies = movies;
    }

    @Bindable
    public String getOriginal_title() {
        return original_title;
    }

    @Bindable
    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    @Bindable
    public void setTitle(String title) {
        this.title = title;
    }

    @Bindable
    public String getBackdrop_path() {
        return backdrop_path;
    }

    @Bindable
    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    @Bindable
    public double getVote_average() {
        return vote_average;
    }

    @Bindable
    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }
}
