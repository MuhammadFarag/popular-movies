package com.muhammadfarag.popularmovies.details.trailers;

import android.support.v4.app.Fragment;

import com.muhammadfarag.popularmovies.BaseActivity;

/**
 * Created by muhammadfarag on 10/3/15.
 */
public class TrailersActivity extends BaseActivity {
    @Override
    protected Fragment createFragment() {
        return  TrailersFragment.newInstance(getIntent().getIntExtra("extra_movie_id",0));
    }
}
