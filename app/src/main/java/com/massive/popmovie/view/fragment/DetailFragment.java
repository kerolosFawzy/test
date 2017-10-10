package com.massive.popmovie.view.fragment;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.massive.popmovie.DataBase.DBContentProvider;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.massive.popmovie.R;
import com.massive.popmovie.Utlis.Constant;
import com.massive.popmovie.databinding.DetialFragmentBinding;
import com.massive.popmovie.model.Movie;
import com.massive.popmovie.view.MainActivity;

import java.net.URI;

import butterknife.OnClick;


public class DetailFragment extends android.app.Fragment {
    private Movie movies = GridFragment.movie;
    private DetialFragmentBinding binding;
    private Context context = getActivity();
    private ContentValues contentValues;
    private int Flag = 0;

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

        binding.FavouritButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Flag == 0)
                    Flag = 1;
                else
                    Flag = 0;

                if (Flag == 1) {
                    addData();
                    Uri uri = getActivity().getContentResolver().insert(Constant.Entry.FULL_URI, contentValues);
                    if (uri != null) {
                        binding.FavouritButton.setText("Remove From Favourit");
                    }
                }else {
                    //// TODO: 10/10/2017  renove from database
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
