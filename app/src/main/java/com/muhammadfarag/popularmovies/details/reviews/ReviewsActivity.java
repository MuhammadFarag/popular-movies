package com.muhammadfarag.popularmovies.details.reviews;

import android.support.v4.app.Fragment;

import com.muhammadfarag.popularmovies.BaseActivity;

/**
 * Created by muhammadfarag on 10/3/15.
 */
public class ReviewsActivity extends BaseActivity {
    @Override
    protected Fragment createFragment() {
        return ReviewsFragment.newInstance(getIntent().getIntExtra("extra_movie_id", 0));
    }
}
