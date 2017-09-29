package com.massive.popmovie.Interfaces;

import com.massive.popmovie.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApi {

    @GET("discover/movie")
    Call<MovieResponse> getMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MovieResponse> getToRated(@Query("api_key") String apiKey);

    @GET("movie/popular")
    Call<MovieResponse> getPopluar(@Query("api_key") String apiKey);

}
