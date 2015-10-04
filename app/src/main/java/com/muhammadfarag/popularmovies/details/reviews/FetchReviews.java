package com.muhammadfarag.popularmovies.details.reviews;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.muhammadfarag.popularmovies.details.DataSetUpdateListener;
import com.muhammadfarag.popularmovies.details.MovieServerConnector;
import com.muhammadfarag.popularmovies.details.FetchMovieElements;

/**
 * Created by muhammadfarag on 10/3/15.
 */
public class FetchReviews extends FetchMovieElements {


    public FetchReviews(Activity activity, DataSetUpdateListener listener) {
        super(activity, listener);
    }

    @NonNull
    @Override
    protected MovieServerConnector setServerConnector(Integer[] params) {
        return new MovieReviewsServerConnector(mActivity, params[0]);
    }

}
