package com.muhammadfarag.popularmovies.details;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.Menu;

import com.muhammadfarag.popularmovies.BaseActivity;
import com.muhammadfarag.popularmovies.Movie;
import com.muhammadfarag.popularmovies.details.reviews.ReviewsActivity;
import com.muhammadfarag.popularmovies.details.trailers.TrailersActivity;


public class DetailsActivity extends BaseActivity implements DetailsActivityFragment.CallBacks {


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

    @Override
    public void showReviews() {
        startActivity(new Intent(this, ReviewsActivity.class));
    }

    @Override
    public void showTrailers() {
        startActivity(new Intent(this, TrailersActivity.class));
    }
}
