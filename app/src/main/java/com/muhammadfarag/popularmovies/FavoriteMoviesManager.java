package com.muhammadfarag.popularmovies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

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

    private MoviesDatabaseHelper db;

    private FavoriteMoviesManager(Context context) {
        db = new MoviesDatabaseHelper(context);
    }

    public static FavoriteMoviesManager create(Context context) {
        if (sMoviesManager == null) {
            sMoviesManager = new FavoriteMoviesManager(context);
        }
        return sMoviesManager;
    }

    public void add(Movie movie) {
        SQLiteDatabase database = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MoviesContract.MovieEntry.COLUMN_ID, movie.getId());
        values.put(MoviesContract.MovieEntry.COLUMN_TITLE, movie.getOriginalTitle());
        values.put(MoviesContract.MovieEntry.COLUMN_RATING, movie.getUserRating());
        values.put(MoviesContract.MovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
        values.put(MoviesContract.MovieEntry.COLUMN_PLOT, movie.getPlotSynopsis());
        values.put(MoviesContract.MovieEntry.COLUMN_POSTER_URL, movie.getPosterUrl());

        database.insert(MoviesContract.MovieEntry.TABLE_NAME, null, values);
        database.close();
    }

    public void remove(Movie movie) {
        Log.d("zosta", "Movie to be delted: " + movie);
        Log.d("zosta", "Movie id: " + String.valueOf(movie.getId()));

        SQLiteDatabase database = db.getWritableDatabase();
        int result = database.delete(MoviesContract.MovieEntry.TABLE_NAME, MoviesContract.MovieEntry.COLUMN_ID + " = " + movie.getId(), null);
        Log.d("zosta", "deleting data result: " + result);
        database.close();
    }

    public boolean isFavorite(Movie movie) {
        SQLiteDatabase database = db.getWritableDatabase();
        Cursor cursor = sQueryBuilder.query(database, null, null, null, null, null, null);
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
        List<Movie> movies = new ArrayList<>();
        SQLiteDatabase database = db.getWritableDatabase();
        Cursor cursor = sQueryBuilder.query(database, null, null, null, null, null, null);
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
