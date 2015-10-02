package com.muhammadfarag.popularmovies.list;

import android.support.v4.app.Fragment;

import com.muhammadfarag.popularmovies.BaseActivity;

public class MovieListActivity extends BaseActivity {


    @Override
    protected Fragment createFragment() {
        return new MovieListFragment();
    }
}
