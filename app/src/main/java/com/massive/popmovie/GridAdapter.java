package com.massive.popmovie;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.massive.popmovie.Utlis.Constant;
import com.massive.popmovie.model.Movie;

import java.util.ArrayList;

/**
 * Created by minafaw on 9/25/2017.
 */

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {

    private ArrayList<Movie> mMovie;
    private static Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.ivMoviePoster);
        }


        @Override
        public void onClick(View view) {
//            Movie movie=getItem(getAdapterPosition());
//            this.postItemListener.onPostClick(movie.getId());
//            notifyDataSetChanged();
            Toast.makeText(mContext, "hi", Toast.LENGTH_SHORT).show();
        }
    }

    public GridAdapter(Context mcontext, ArrayList<Movie> arrayList) {
        this.mContext = mcontext;
        this.mMovie = arrayList;
    }

    @Override
    public GridAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GridAdapter.ViewHolder holder, int position) {
        Glide.with(mContext)
                .load(Constant.POSTER_URL + mMovie.get(position).getPoster_path())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mMovie.size();
    }

    private Movie getItem(int adapterPosition) {
        return mMovie.get(adapterPosition);
    }

}
