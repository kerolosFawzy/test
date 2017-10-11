package com.massive.popmovie.view.fragment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.massive.popmovie.R;
import com.massive.popmovie.Utlis.Constant;
import com.massive.popmovie.databinding.DetialFragmentBinding;
import com.massive.popmovie.model.Movie;

import java.util.ArrayList;


public class DetailFragment extends android.app.Fragment {
    private Movie movies = GridFragment.movie;
    private DetialFragmentBinding binding;
    private Context context = getActivity();
    private ContentValues contentValues;
    private int Flag;
    ArrayList<Movie> FVmovies ;
    Movie movieF;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

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
        RatingBar ratingBar = binding.ratingBar;
        try {
            ratingBar.setRating((float) movies.getVote_average());
        } catch (NullPointerException e) {
            Log.e("rating bar", e.getMessage());
        }
        getDataFromCursor();
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

    @BindingAdapter({"bind:poster_path"})
    public static void loadImage(ImageView view, String url) {
        Glide.with(view.getContext()).load(Constant.POSTER_URL + url)
                .error(R.drawable.play)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view);
    }

}
