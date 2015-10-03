package com.muhammadfarag.popularmovies.details;

import android.support.v4.app.Fragment;

import com.muhammadfarag.popularmovies.BaseActivity;

/**
 * Created by muhammadfarag on 10/3/15.
 */
public class TrailersActivity extends BaseActivity {
    @Override
    protected Fragment createFragment() {
        return new TrailersFragment();
    }
}
