package com.muhammadfarag.popularmovies.details;

import android.support.v4.app.Fragment;
import android.view.Menu;

import com.muhammadfarag.popularmovies.BaseActivity;


public class DetailsActivity extends BaseActivity {


    @Override
    protected Fragment createFragment() {
        return new DetailsActivityFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

}
