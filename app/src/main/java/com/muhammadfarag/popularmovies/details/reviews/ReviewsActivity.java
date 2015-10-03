package com.muhammadfarag.popularmovies.details.reviews;

import android.support.v4.app.Fragment;

import com.muhammadfarag.popularmovies.BaseActivity;
import com.muhammadfarag.popularmovies.details.trailers.TrailersFragment;

/**
 * Created by muhammadfarag on 10/3/15.
 */
public class ReviewsActivity extends BaseActivity {
    @Override
    protected Fragment createFragment() {
        return  ReviewsFragment.newInstance(218);
    }
}
