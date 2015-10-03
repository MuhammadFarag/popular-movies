package com.muhammadfarag.popularmovies.list;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.muhammadfarag.popularmovies.BaseActivity;
import com.muhammadfarag.popularmovies.R;
import com.muhammadfarag.popularmovies.details.DetailsActivityFragment;
import com.muhammadfarag.popularmovies.details.reviews.ReviewsFragment;
import com.muhammadfarag.popularmovies.details.trailers.TrailersFragment;

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
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_details, ReviewsFragment.newInstance(218)).addToBackStack(null).commit();

//                startActivity(new Intent(this, ReviewsActivity.class));
    }

    @Override
    public void showTrailers() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_details, TrailersFragment.newInstance(218)).addToBackStack(null).commit();

//        startActivity(new Intent(this, TrailersActivity.class));
    }
}
