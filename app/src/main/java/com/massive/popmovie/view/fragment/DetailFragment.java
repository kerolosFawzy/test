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
import com.massive.popmovie.model.Movie;

import java.util.ArrayList;

/**
 * Created by minafaw on 9/27/2017.
 */

public class DetailFragment extends Fragment implements ResponseCallBack {
    ArrayList<Movie> movies;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = DataBindingUtil.inflate(inflater, R.layout.detial_fragment, container, false).getRoot();
//        FragmentMainBinding  binding;
//        binding = DataBindingUtil.inflate(inflater, R.layout.detial_fragment, container, false);
        return view;
    }

    @Override
    public void OnSuccess(ArrayList<Movie> message) {
        this.movies = message;
    }
}
