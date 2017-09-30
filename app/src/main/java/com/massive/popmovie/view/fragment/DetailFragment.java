package com.massive.popmovie.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.massive.popmovie.view.MainActivity;


public class DetailFragment extends android.app.Fragment {
    private Movie movies = GridFragment.movie;
    private DetialFragmentBinding binding;
    Context context = getActivity();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.detial_fragment, container, false);
        binding.setMovie(movies);
        RatingBar ratingBar= binding.ratingBar;
        ratingBar.setRating((float) movies.getVote_average());
        return binding.getRoot();
    }

    @BindingAdapter({"bind:poster_path"})
    public static void loadImage(ImageView view, String url) {
        Glide.with(view.getContext()).load(Constant.POSTER_URL+url)
                .error(R.drawable.play)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        GridFragment.movie=null;
    }
}
