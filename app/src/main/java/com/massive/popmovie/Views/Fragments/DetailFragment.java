package com.massive.popmovie.Views.Fragments;

import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.database.DatabaseUtilsCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.massive.popmovie.Interfaces.MovieApi;
import com.massive.popmovie.Network.RetrofitClient;
import com.massive.popmovie.R;
import com.massive.popmovie.Utlis.Constant;
import com.massive.popmovie.Utlis.NetworkCheck;
import com.massive.popmovie.databinding.DetialFragmentBinding;
import com.massive.popmovie.model.Movie;
import com.massive.popmovie.model.Reviews;
import com.massive.popmovie.model.Trailer;
import com.massive.popmovie.model.TrailerResponse;
import com.massive.popmovie.model.reviewsResponse;
import com.massive.popmovie.Views.ReviewActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailFragment extends android.app.Fragment {
    private Movie movies = GridFragment.movie;
    private static DetialFragmentBinding binding;
    private Context context = getActivity();
    private ContentValues contentValues;
    private int Flag;
    private MovieApi movieApi;
    private ArrayList<Trailer> trailers = new ArrayList<>();
    private Call<reviewsResponse> call;
    private ArrayList<Movie> FVmovies;
    private Movie movieF;
    private String Unavailable = "This video Unavailable";
    private Parcelable parcelable;

    private void getDataFromCursor() {
        Cursor moviesCursor = getActivity().getContentResolver()
                .query(Constant.Entry.FULL_URI, null, null, null, null);
        FVmovies = new ArrayList<>();
        while (moviesCursor.moveToNext()) {
            movieF = new Movie();
            movieF.setId(moviesCursor.getLong(moviesCursor.getColumnIndex("ID")));
            movieF.setTitle(moviesCursor.getString(moviesCursor.getColumnIndex("name")));
            movieF.setOverview(moviesCursor.getString(moviesCursor.getColumnIndex("overview")));
            movieF.setPoster_path(moviesCursor.getString(moviesCursor.getColumnIndex("poster")));
            movieF.setRelease_date(moviesCursor.getString(moviesCursor.getColumnIndex("release_date")));
            movieF.setVote_average(moviesCursor.getFloat(moviesCursor.getColumnIndex("vote_averge")));
            FVmovies.add(movieF);
        }
    }

    private boolean CheckInDataBase() {
        if (FVmovies == null)
            return false;
        else {
            int j = 0;
            while (FVmovies.size() > j) {
                if (FVmovies.get(j).getId() == movies.getId())
                    return true;
                j++;
            }
        }
        return false;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.detial_fragment, container, false);
        binding.setMovie(movies);
        try {
            binding.ratingBar.setRating((float) movies.getVote_average());
        } catch (NullPointerException e) {
            Log.e("rating bar", e.getMessage());
        }
        getDataFromCursor();
        CallVideoRetrofit();

        if (CheckInDataBase()) {
            binding.FavouritButton.setText((R.string.RemoveButton));
            Flag = 0;
        } else Flag = 1;

        binding.FavouritButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Flag == 1) {
                    addData();
                    Uri uri = getActivity().getContentResolver().insert(Constant.Entry.FULL_URI, contentValues);
                    if (uri != null) {
                        binding.FavouritButton.setText(R.string.RemoveButton);
                        Flag = 0;
                    }
                } else {
                    long id = movies.getId();
                    String _id = Long.toString(id);
                    Uri uri = Constant.Entry.FULL_URI;
                    uri = uri.buildUpon().appendPath(_id).build();
                    getActivity().getContentResolver().delete(uri, null, null);
                    binding.FavouritButton.setText(R.string.AddButton);
                    Flag = 1;
                }

            }
        });


        binding.buttonShowView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (trailers.get(0).getKey() != null)
                    try {
                        watchYoutubeVideo(trailers.get(0).getKey());
                    } catch (Exception e) {
                        Constant.MakeToast(context, Unavailable);
                    }
                else
                    Constant.MakeToast(context, Unavailable);
            }
        });

        binding.buttonShowView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (trailers.get(1).getKey() != null)
                    try {
                        watchYoutubeVideo(trailers.get(1).getKey());
                    } catch (Exception e) {
                        Constant.MakeToast(context, Unavailable);
                    }
                else
                    Constant.MakeToast(context, Unavailable);
            }
        });
        binding.buttonShowView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (trailers != null && trailers.size() > 2) {
                    if (trailers.get(2).getKey() != null) {
                        try {
                            watchYoutubeVideo(trailers.get(2).getKey());
                        } catch (Exception e) {
                            Constant.MakeToast(context, Unavailable);
                        }
                    } else
                        Constant.MakeToast(context, Unavailable);
                }
            }
        });

        binding.ReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CallReviewsRetrofit();
            }
        });
        return binding.getRoot();
    }

    private void addData() {
        contentValues = new ContentValues();
        contentValues.put(Constant.Entry.ID, movies.getId());
        contentValues.put(Constant.Entry.POSTER_URL, movies.getPoster_path());
        contentValues.put(Constant.Entry.NAME, movies.getOriginal_title());
        contentValues.put(Constant.Entry.DATE, movies.getRelease_date());
        contentValues.put(Constant.Entry.VOTE, movies.getVote_average());
        contentValues.put(Constant.Entry.OVERVIEW, movies.getOverview());
    }

    private void CallVideoRetrofit() {
        if (NetworkCheck.isNetworkAvailable(getActivity())) {
            movieApi = RetrofitClient.getClient().create(MovieApi.class);
            String id = String.valueOf(movies.getId());
            Call<TrailerResponse> responseCall = movieApi.getTrailers(id, Constant.APIKEY);

            responseCall.enqueue(new Callback<TrailerResponse>() {
                @Override
                public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                    trailers = response.body().getResults();
                }

                @Override
                public void onFailure(Call<TrailerResponse> call, Throwable t) {
                    call.cancel();
                    Constant.MakeToast(context, "Respone error");
                }
            });
        } else
            Constant.MakeToast(getActivity(), "Turn on your network");

    }

    private void CallReviewsRetrofit() {
        if (NetworkCheck.isNetworkAvailable(getActivity())) {
            movieApi = RetrofitClient.getClient().create(MovieApi.class);
            String id = String.valueOf(movies.getId());
            call = movieApi.getReviews(id, Constant.APIKEY);
            call.enqueue(new Callback<reviewsResponse>() {
                @Override
                public void onResponse(Call<reviewsResponse> call, Response<reviewsResponse> response) {
                    final ArrayList<Reviews> reviewses = response.body().getResults();

                    if (reviewses.isEmpty() || reviewses.size() == 0) {
                    } else {
                        Intent intent = new Intent(getActivity(), ReviewActivity.class);
                        intent.putExtra("data", reviewses);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<reviewsResponse> call, Throwable t) {

                }
            });
        } else
            Constant.MakeToast(getActivity(), "Turn on your network");

        boolean b = call.isExecuted();
    }

    private void watchYoutubeVideo(String key) {
        if (NetworkCheck.isNetworkAvailable(getActivity())) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.BASE_YOUTUBE + key));
                getActivity().startActivity(intent);
            } catch (ActivityNotFoundException ex) {
                Constant.MakeToast(context, "try again");
            }
        } else Constant.MakeToast(context, "Turn on your Network");

    }

    @BindingAdapter({"bind:poster_path"})
    public static void loadImage(ImageView view, String url) {
        Glide.with(view.getContext()).load(Constant.POSTER_URL + url)
                .error(R.drawable.play)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view);
    }

}
