package com.massive.popmovie.Interfaces;

import com.massive.popmovie.model.MovieResponse;
import com.massive.popmovie.model.TrailerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {

    @GET("discover/movie")
    Call<MovieResponse> getMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MovieResponse> getToRated(@Query("api_key") String apiKey);

    @GET("movie/popular")
    Call<MovieResponse> getPopluar(@Query("api_key") String apiKey);

    @GET("movie/{id}/videos")
    Call<TrailerResponse> getTrailers(@Path("id") String id , @Query("api_key") String apiKey);
}
