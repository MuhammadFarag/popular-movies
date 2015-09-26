package com.muhammadfarag.popularmovies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    public void add(int id) {
        SQLiteDatabase database = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", id);
        database.insert("movie", null, values);
        database.close();
    }

    public void remove(int id) {
        SQLiteDatabase database = db.getWritableDatabase();
        database.delete("movie", "name = ?", new String[]{String.valueOf(id)});
        database.close();
    }

    public boolean isFavorite(int id) {
        SQLiteDatabase database = db.getWritableDatabase();
        Cursor cursor = database.query("movie", null, null, null, null, null, null);
        int columnIndex = cursor.getColumnIndex("name");
        while (cursor.moveToNext()) {
            if (cursor.getInt(columnIndex) == id) {
                cursor.close();
                return true;
            }
        }
        cursor.close();
        return false;
    }

    private class MoviesDatabase extends SQLiteOpenHelper {

        public MoviesDatabase(Context context, int version) {
            super(context, "movie.db", null, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("CREATE TABLE movie(_ID INT PRIMARY KEY, name);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            // upgrade is not supported yet
        }
    }

}
