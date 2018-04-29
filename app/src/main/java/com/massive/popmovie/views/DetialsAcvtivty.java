package com.massive.popmovie.views;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.massive.popmovie.R;
import com.massive.popmovie.views.fragments.DetailFragment;

public class DetialsAcvtivty extends AppCompatActivity {
    private static final String TAG_RETAINED_FRAGMENT = "DetailFragment";
    android.app.Fragment Detial;

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Detial = getFragmentManager().getFragment(outState, TAG_RETAINED_FRAGMENT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detials);

        if (savedInstanceState != null) {
            Detial = getFragmentManager().getFragment(savedInstanceState, TAG_RETAINED_FRAGMENT);
        } else
            getFragmentManager().beginTransaction().replace(R.id.DetialContainer, new DetailFragment())
                    .commit();
    }
}
