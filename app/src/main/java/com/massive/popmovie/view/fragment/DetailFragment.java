package com.massive.popmovie.view.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.massive.popmovie.Interfaces.ResponseCallBack;
import com.massive.popmovie.R;
import com.massive.popmovie.Utlis.Constant;
import com.massive.popmovie.databinding.DetialFragmentBinding;
import com.massive.popmovie.model.Movie;

import java.util.ArrayList;

public class DetailFragment extends Fragment implements ResponseCallBack {
    Movie movies;
    String url = Constant.POSTER_URL + movies.getPoster_path();
    DetialFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.detial_fragment, container, false);
        binding.setMovie(movies);
        return binding.getRoot();
    }

    @Override
    public void OnSuccess(Movie message) {
        this.movies = message;
    }
}
