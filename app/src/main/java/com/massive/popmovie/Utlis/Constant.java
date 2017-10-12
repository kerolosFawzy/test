package com.massive.popmovie.Utlis;

import android.content.Context;
import android.net.Uri;
import android.provider.BaseColumns;
import android.widget.Toast;

/**
 * Created by minafaw on 9/25/2017.
 */

public class Constant {

    //https://api.themoviedb.org/3/discover/movie?api_key=5372c5a4714c65cf3e361699c681a136
    //https://api.themoviedb.org/3/movie/211672/videos?api_key=5372c5a4714c65cf3e361699c681a136

    public static String BASE_YOUTUBE = "https://www.youtube.com/watch?v=";
    public static String POSTER_URL = "http://image.tmdb.org/t/p/w185";
    public static String API = "api_key";
    public static String APIKEY = "5372c5a4714c65cf3e361699c681a136";
    public static String AUTHORTY = "com.massive.popmovie";
    private static final Uri BASE_URI = Uri.parse("content://" + AUTHORTY);
    public static String PATH = "MoviesTable";

    public static void MakeToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static final class Entry implements BaseColumns {
        public static final Uri FULL_URI = BASE_URI.buildUpon().appendPath(PATH).build();

        public static String DATABASE_TABLE = "MoviesTable";

        public static String _ID = "_ID";
        public static String ID = "ID";
        public static String POSTER_URL = "poster";
        public static String NAME = "name";
        public static String DATE = "release_date";
        public static String VOTE = "vote_averge";
        public static String OVERVIEW = "overview";

    }
}
