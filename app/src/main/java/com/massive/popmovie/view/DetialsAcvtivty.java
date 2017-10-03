package com.massive.popmovie.view;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.massive.popmovie.R;
import com.massive.popmovie.view.fragment.DetailFragment;

public class DetialsAcvtivty extends AppCompatActivity {

    android.app.Fragment Detial;

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Detial = getFragmentManager().getFragment(outState, "DetailFragment");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detials);

        if (savedInstanceState != null) {
            Detial = getFragmentManager().getFragment(savedInstanceState, "DetailFragment");
        } else
            getFragmentManager().beginTransaction().replace(R.id.DetialContainer, new DetailFragment())
                    .commit();
    }
}
