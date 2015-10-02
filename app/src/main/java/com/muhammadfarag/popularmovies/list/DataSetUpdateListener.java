package com.muhammadfarag.popularmovies.list;

import com.muhammadfarag.popularmovies.Movie;

import java.util.List;

/**
 * Project: Popular Movies
 * Created by muhammad on 14/07/15.
 */
public interface DataSetUpdateListener {
    public void onDataSetUpdated(List<Movie> movies);
}
