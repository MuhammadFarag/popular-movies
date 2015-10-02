package com.muhammadfarag.popularmovies.list;

import android.support.v4.app.Fragment;

import com.muhammadfarag.popularmovies.BaseActivity;
import com.muhammadfarag.popularmovies.R;

public class MovieListActivity extends BaseActivity {


    @Override
    protected Fragment createFragment() {
        return new MovieListFragment();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_master_detail;
    }
}
