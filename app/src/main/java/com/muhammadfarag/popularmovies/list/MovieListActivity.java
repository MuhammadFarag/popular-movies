package com.muhammadfarag.popularmovies.list;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.muhammadfarag.popularmovies.BaseActivity;
import com.muhammadfarag.popularmovies.R;
import com.muhammadfarag.popularmovies.details.DetailsActivityFragment;
import com.muhammadfarag.popularmovies.details.reviews.ReviewsActivity;
import com.muhammadfarag.popularmovies.details.trailers.TrailersActivity;

public class MovieListActivity extends BaseActivity implements DetailsActivityFragment.CallBacks {


    @Override
    protected Fragment createFragment() {
        return new MovieListFragment();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_movies_list;
    }

    @Override
    public void showReviews() {
        startActivity(new Intent(this, ReviewsActivity.class));
    }

    @Override
    public void showTrailers() {
        startActivity(new Intent(this, TrailersActivity.class));
    }
}
