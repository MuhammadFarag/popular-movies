package com.muhammadfarag.popularmovies.details.trailers;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.muhammadfarag.popularmovies.details.DataSetUpdateListener;
import com.muhammadfarag.popularmovies.details.FetchMovieElements;
import com.muhammadfarag.popularmovies.details.MovieServerConnector;

/**
 * Created by muhammadfarag on 10/3/15.
 */
public class FetchTrailers extends FetchMovieElements {


    public FetchTrailers(Activity activity, DataSetUpdateListener listener) {
        super(activity, listener);
    }

    @Override
    @NonNull
    protected MovieServerConnector setServerConnector(Integer[] params) {
        return new MovieTrailersServerConnector(mActivity, params[0]);
    }

}
