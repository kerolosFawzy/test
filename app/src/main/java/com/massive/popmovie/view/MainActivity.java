package com.massive.popmovie.view;

import android.os.PersistableBundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.massive.popmovie.R;
import com.massive.popmovie.view.fragment.DetailFragment;
import com.massive.popmovie.view.fragment.GridFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getFragmentManager().beginTransaction().replace(R.id.FragmentContainer, new GridFragment())
                .commit();

    }

}