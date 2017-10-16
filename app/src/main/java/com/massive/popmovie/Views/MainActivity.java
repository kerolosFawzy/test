package com.massive.popmovie.Views;

import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.massive.popmovie.R;
import com.massive.popmovie.Views.Fragments.GridFragment;

public class MainActivity extends AppCompatActivity {
    android.app.Fragment GridFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        GridFragment = getFragmentManager().getFragment(savedInstanceState, "GridFragment");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null)
            getFragmentManager().beginTransaction().replace(R.id.FragmentContainer, new GridFragment())
                    .commit();
        else
            GridFragment = getFragmentManager().getFragment(savedInstanceState, "GridFragment");

    }

}