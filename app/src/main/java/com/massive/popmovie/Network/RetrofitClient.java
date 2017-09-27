package com.massive.popmovie.Network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by minafaw on 9/25/2017.
 */

public class RetrofitClient {
    private static Retrofit retrofit = null;
    public static String BASE_URL = "https://API.themoviedb.org/3/";

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}