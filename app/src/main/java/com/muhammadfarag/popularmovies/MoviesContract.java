package com.muhammadfarag.popularmovies;

import android.provider.BaseColumns;

/**
 * Created by muhammadfarag on 10/30/15.
 */
public class MoviesContract {

    class MovieEntry implements BaseColumns {
        public static final String TABLE_NAME = "movie";
        public static final String dd = "CREATE TABLE movie(_ID INT PRIMARY KEY, id, title, rating, release_date, plot, poster_url);";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_RATING = "rating";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_PLOT = "plot";
        public static final String COLUMN_POSTER_URL = "poster_url";

    }
}
