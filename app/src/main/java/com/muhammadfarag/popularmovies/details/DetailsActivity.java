package com.muhammadfarag.popularmovies.details;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.Menu;

import com.muhammadfarag.popularmovies.BaseActivity;
import com.muhammadfarag.popularmovies.Movie;


public class DetailsActivity extends BaseActivity {


    @Override
    protected Fragment createFragment() {
        Intent intent = getIntent();
        Movie movie = intent.getParcelableExtra("movie");
        return DetailsActivityFragment.newInstance(movie);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

}
