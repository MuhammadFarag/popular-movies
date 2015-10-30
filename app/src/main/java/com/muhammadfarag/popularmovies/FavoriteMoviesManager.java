package com.muhammadfarag.popularmovies;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by muhammadfarag on 9/26/15.
 */
public class FavoriteMoviesManager {
    private static final SQLiteQueryBuilder sQueryBuilder;
    private static FavoriteMoviesManager sMoviesManager;

    static {
        sQueryBuilder = new SQLiteQueryBuilder();
        sQueryBuilder.setTables(MoviesContract.MovieEntry.TABLE_NAME);
    }

    private final ContentResolver mContentResolver;


    private FavoriteMoviesManager(Context context) {
        mContentResolver = context.getContentResolver();
    }

    public static FavoriteMoviesManager create(Context context) {
        if (sMoviesManager == null) {
            sMoviesManager = new FavoriteMoviesManager(context);
        }
        return sMoviesManager;
    }

    public void add(Movie movie) {

        ContentValues values = new ContentValues();
        values.put(MoviesContract.MovieEntry.COLUMN_ID, movie.getId());
        values.put(MoviesContract.MovieEntry.COLUMN_TITLE, movie.getOriginalTitle());
        values.put(MoviesContract.MovieEntry.COLUMN_RATING, movie.getUserRating());
        values.put(MoviesContract.MovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
        values.put(MoviesContract.MovieEntry.COLUMN_PLOT, movie.getPlotSynopsis());
        values.put(MoviesContract.MovieEntry.COLUMN_POSTER_URL, movie.getPosterUrl());
        mContentResolver.insert(MoviesContract.MovieEntry.CONTENT_URI, values);
    }

    public void remove(Movie movie) {
        mContentResolver.delete(MoviesContract.MovieEntry.CONTENT_URI, MoviesContract.MovieEntry.COLUMN_ID + " = " + movie.getId(), null);
    }

    public boolean isFavorite(Movie movie) {
        // FIXME: Improve the implementation of isFavorite to query for a specific record
        Cursor cursor = mContentResolver.query(MoviesContract.MovieEntry.CONTENT_URI, null, null, null, null);
        int columnIndex = cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_ID);
        while (cursor.moveToNext()) {
            if (cursor.getInt(columnIndex) == movie.getId()) {
                cursor.close();
                return true;
            }
        }
        cursor.close();
        return false;
    }

    public List<Movie> getMovies() {

        Cursor cursor = mContentResolver.query(MoviesContract.MovieEntry.CONTENT_URI, null, null, null, null);
        List<Movie> movies = new ArrayList<>();
        while (cursor.moveToNext()) {
            Movie movie = new Movie(cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_TITLE)),
                    cursor.getDouble(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_RATING)),
                    cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_RELEASE_DATE)),
                    cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_PLOT)),
                    cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_POSTER_URL)),
                    cursor.getInt(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_ID)));
            movies.add(movie);
        }
        cursor.close();
        return movies;
    }

}
