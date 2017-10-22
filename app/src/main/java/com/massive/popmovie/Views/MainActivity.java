package com.massive.popmovie.Views;

import android.app.FragmentManager;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.massive.popmovie.R;
import com.massive.popmovie.Views.Fragments.GridFragment;

public class MainActivity extends AppCompatActivity {
    android.app.Fragment GridFragment;
    private static final String TAG_RETAINED_FRAGMENT = "RetainedFragment";

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
//        super.onCreate(savedInstanceState, persistentState);
//        GridFragment = getFragmentManager().getFragment(savedInstanceState, "GridFragment");
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        if (savedInstanceState == null)
//            getFragmentManager().beginTransaction().replace(R.id.FragmentContainer, new GridFragment())
//                    .commit();
//        else
//            GridFragment = getFragmentManager().getFragment(savedInstanceState, "GridFragment");
        FragmentManager fm = getFragmentManager();
        GridFragment =  fm.findFragmentByTag(TAG_RETAINED_FRAGMENT);

        if (GridFragment == null) {
            GridFragment = new GridFragment();
            fm.beginTransaction().add(R.id.FragmentContainer,GridFragment, TAG_RETAINED_FRAGMENT).commit();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        if(isFinishing()) {
            FragmentManager fm = getFragmentManager();
            fm.beginTransaction().remove(GridFragment).commit();
        }

    }

}