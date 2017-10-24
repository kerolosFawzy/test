package com.massive.popmovie.Views.Fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.massive.popmovie.GridAdapter;
import com.massive.popmovie.Interfaces.ResponseCallBack;
import com.massive.popmovie.Interfaces.MovieApi;
import com.massive.popmovie.Network.RetrofitClient;
import com.massive.popmovie.R;
import com.massive.popmovie.Utlis.Constant;
import com.massive.popmovie.Utlis.NetworkCheck;
import com.massive.popmovie.model.Movie;
import com.massive.popmovie.model.MovieResponse;
import com.massive.popmovie.Views.DetialsAcvtivty;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GridFragment extends Fragment {

    private GridAdapter mAdapter;
    private RecyclerView mRecyclerView;
    GridLayoutManager layoutManager;
    private MovieApi mService;
    public static Movie movie;
    private static String Flag = "normal";
    private Call<MovieResponse> call;
    private Context mcontext = getActivity();
    private View view;
    public ArrayList<Movie> FVmovies;
    Movie movieF;
    private String LastPostiionKey = "LastPostiionKey";
    private int scrollPosition;
    Parcelable parcelable;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        try {
            parcelable = layoutManager.onSaveInstanceState();
        } catch (NullPointerException e) {

        }

        outState.putParcelable(LastPostiionKey, parcelable);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null)
            parcelable = savedInstanceState.getParcelable(LastPostiionKey);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Flag.equals( "Favourite"))
            FetchFavourit();
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.grid_fragment, container, false);
        callfragment();
        return view;
    }

    private void setScroll(int scroll) {
        scrollPosition = scroll;
        Constant.MakeToast(getActivity(), "my value = " + scrollPosition);
        if (scrollPosition != 0)
            mRecyclerView.smoothScrollToPosition(scrollPosition);
    }

    private void FetchFavourit() {
        getDataFromCursor();
        if (FVmovies.size() != 0) {
            GridAdapter adapter = new GridAdapter(getActivity(), FVmovies, new ResponseCallBack() {
                @Override
                public void OnSuccess(Movie message) {
                    movie = message;
                    Intent intent = new Intent(getActivity(), DetialsAcvtivty.class);
                    startActivity(intent);
                }
            });
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.post(new Runnable() {
                @Override
                public void run() {
                    mRecyclerView.getLayoutManager().onRestoreInstanceState(parcelable);
                }
            });
        }
    }

    private boolean callfragment() {
        if (NetworkCheck.isNetworkAvailable(getActivity()) || Flag.equals("Favourite")) {
            mRecyclerView = (RecyclerView) view.findViewById(R.id.GridRecyclerView);
            layoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
            mRecyclerView.setLayoutManager(this.layoutManager);
            mService = RetrofitClient.getClient().create(MovieApi.class);
            switch (Flag) {
                case "normal":
                    call = mService.getMovies(Constant.APIKEY);
                    break;
                case "popular":
                    call = mService.getPopluar(Constant.APIKEY);
                    break;
                case "rated":
                    call = mService.getToRated(Constant.APIKEY);
                    break;
                case "Favourite":
                    FetchFavourit();
                    return true;
            }
            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    ArrayList<Movie> movies = response.body().getResults();

                    GridAdapter adapter = new GridAdapter(getActivity(), movies, new ResponseCallBack() {
                        @Override
                        public void OnSuccess(Movie message) {
                            movie = message;
                            Intent intent = new Intent(getActivity(), DetialsAcvtivty.class);
                            startActivity(intent);
                        }
                    });
                    mRecyclerView.setAdapter(adapter);

                    mRecyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            mRecyclerView.getLayoutManager().onRestoreInstanceState(parcelable);
                        }
                    });

                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {

                }
            });
            return true;
        } else
            showErrormessage();
        return false;
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.setting, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.normal:
                Flag = "normal";
                break;
            case R.id.sortByPopular:
                Flag = "popular";
                break;
            case R.id.sortByTopRated:
                Flag = "rated";
                break;
            case R.id.Favourite:
                Flag = "Favourite";
                break;
        }
        callfragment();

        return super.onOptionsItemSelected(item);
    }

    public void showErrormessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setTitle("No Internet");
        builder.setMessage("Internet is required. Please Retry.");
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                callfragment();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        Toast.makeText(getActivity(), "Network Unavailable!", Toast.LENGTH_LONG).show();
    }

}

