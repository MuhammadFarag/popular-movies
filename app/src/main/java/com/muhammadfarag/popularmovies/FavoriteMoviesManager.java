package com.muhammadfarag.popularmovies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by muhammadfarag on 9/26/15.
 */
public class FavoriteMoviesManager {
    private static FavoriteMoviesManager sMoviesManager;
    private MoviesDatabase db;

    private FavoriteMoviesManager(Context context) {
        db = new MoviesDatabase(context, 1);
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
        values.put("id", movie.getId());
        values.put("title", movie.getOriginalTitle());
        values.put("rating", movie.getUserRating());
        values.put("release_date", movie.getReleaseDate());
        values.put("plot", movie.getPlotSynopsis());
        values.put("poster_url", movie.getPosterUrl());

        database.insert("movie", null, values);
        database.close();
    }

    public void remove(Movie movie) {
        SQLiteDatabase database = db.getWritableDatabase();
        database.delete("movie", "id = ?", new String[]{String.valueOf(movie.getId())});
        database.close();
    }

    public boolean isFavorite(Movie movie) {
        SQLiteDatabase database = db.getWritableDatabase();
        Cursor cursor = database.query("movie", null, null, null, null, null, null);
        int columnIndex = cursor.getColumnIndex("id");
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
        Cursor cursor = database.query("movie", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Movie movie = new Movie(cursor.getString(cursor.getColumnIndex("title")),
                    cursor.getDouble(cursor.getColumnIndex("rating")),
                    cursor.getString(cursor.getColumnIndex("release_date")),
                    cursor.getString(cursor.getColumnIndex("plot")),
                    cursor.getString(cursor.getColumnIndex("poster_url")),
                    cursor.getInt(cursor.getColumnIndex("id")));
            movies.add(movie);
        }
        cursor.close();
        return movies;
    }

    private class MoviesDatabase extends SQLiteOpenHelper {

        public MoviesDatabase(Context context, int version) {
            super(context, "movie.db", null, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("CREATE TABLE movie(_ID INT PRIMARY KEY, id, title, rating, release_date, plot, poster_url);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            // upgrade is not supported yet
        }
    }

}
