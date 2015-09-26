package com.muhammadfarag.popularmovies;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by muhammadfarag on 9/26/15.
 */
public class FavoriteMoviesManager {
    private static FavoriteMoviesManager sMoviesManager;
    private Set<String> mMovieIds;

    private FavoriteMoviesManager() {
        mMovieIds = new HashSet<>();
    }

    public static FavoriteMoviesManager create() {
        if (sMoviesManager == null) {
            sMoviesManager = new FavoriteMoviesManager();
        }
        return sMoviesManager;
    }

    public void add(String id) {
        mMovieIds.add(id);
    }

    public void remove(String id) {
        mMovieIds.remove(id);
    }

    public boolean isFavorite(String id) {
        return mMovieIds.contains(id);
    }

}
