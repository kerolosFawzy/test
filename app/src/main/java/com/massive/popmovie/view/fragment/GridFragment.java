package com.massive.popmovie.view.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.massive.popmovie.GridAdapter;
import com.massive.popmovie.Interfaces.ResponseCallBack;
import com.massive.popmovie.Interfaces.MovieApi;
import com.massive.popmovie.Network.RetrofitClient;
import com.massive.popmovie.R;
import com.massive.popmovie.Utlis.Constant;
import com.massive.popmovie.model.Movie;
import com.massive.popmovie.model.MovieResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GridFragment extends Fragment {

    private GridAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private MovieApi mService;
    Context mcontext = getActivity();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.grid_fragment, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.GridRecyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(mcontext, 2);
        mRecyclerView.setLayoutManager(layoutManager);

        mService = RetrofitClient.getClient().create(MovieApi.class);

        Call<MovieResponse> call = mService.getMovies(Constant.APIKEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                int statusCode = response.code();
                ArrayList<Movie> movies = response.body().getResults();
                GridAdapter adapter = new GridAdapter(getActivity(), movies);
                mRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
        return view;
    }
}

