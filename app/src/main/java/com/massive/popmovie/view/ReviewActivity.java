package com.massive.popmovie.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.massive.popmovie.R;
import com.massive.popmovie.databinding.ActivityReviewBinding;
import com.massive.popmovie.model.Reviews;

import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity {
    private Reviews reviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityReviewBinding reviewBinding = DataBindingUtil.setContentView(this, R.layout.activity_review);
        Intent intent = getIntent();
        ArrayList<Reviews> reviewsArrayList = (ArrayList<Reviews>) intent.getExtras().getSerializable("data");
        if (reviewsArrayList.size() != 0)
            reviews = reviewsArrayList.get(0);
        if (reviews == null)
            reviews = reviewsArrayList.get(1);
        reviewBinding.setReview(reviews);
    }
}
