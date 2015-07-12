package com.muhammadfarag.popularmovies;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Project: Popular Movies
 * Created by muhammad on 01/07/15.
 */
class CustomArrayAdapter extends BaseAdapter {

    private static final int PAGE_SIZE = 20;
    private static final String TAG = "PopMovieApp";
    private final Context context;
    private final int resource;
    private List<Movie> elements;
    private final Object mLock = new Object();
    private int currentPage = 1;
    private ProgressDialog pd;
    private Movie movieAtPositionZero;


    public CustomArrayAdapter(Context context, int resource, List<Movie> elements) {
        this.context = context;
        this.resource = resource;
        this.elements = elements;
    }

    @Override
    public int getCount() {
        return elements.size() == 0 ? 0 : 2000;
    }

    @Override
    public Movie getItem(int position) {
        // Hack to solve the problem of gridView randomly requesting view at position 0
        if (movieAtPositionZero == null) {
            movieAtPositionZero = elements.get(0);
        }
        if (position == 0) {
            return movieAtPositionZero;
        }
        Log.d(TAG, "     2.1 Getting item from position[" + position +
                "] with current page number [" + currentPage +
                "]");
        if (currentPage < position / PAGE_SIZE + 1) {
            currentPage = position / PAGE_SIZE + 1;
            Log.d(TAG, "     2.2 Increment page number to [" + currentPage +
                    "]");
            FetchMoviesData fetchMoviesData = new FetchMoviesData();
            fetchMoviesData.execute(currentPage);
        }
        if (position != 0 && currentPage > position / PAGE_SIZE + 1) {
            currentPage = position / PAGE_SIZE + 1;
            Log.d(TAG, "     2.3 Decrement page number to [" + currentPage +
                    "]");
            FetchMoviesData fetchMoviesData = new FetchMoviesData();
            fetchMoviesData.execute(currentPage);
        }
        Log.d(TAG, "     2.4    Returning movie >>>> " + elements.get(position % PAGE_SIZE).getOriginalTitle());
        return elements.get(position % PAGE_SIZE);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView view = (ImageView) convertView;
        if (view == null) {
            view = (ImageView) LayoutInflater.from(this.context).inflate(this.resource, parent, false);
        }

        Log.d(TAG, "     1.1 <><><><> Getting view for position: [" + position + "]");
        Movie movie = getItem(position);
        Log.d(TAG, "     1.2 <><><><> Getting view for Movie: [" + movie.getOriginalTitle() + "]");
        String url = movie.getPosterUrl();
        Picasso.with(context).load(url).into(view);
//        Picasso.with(context).load(albumURL).error(R.drawable.no_album_art).into(holder.cell_image);


//        view.setImageResource(R.drawable.abc_btn_check_to_on_mtrl_000);
        return view;
    }

    public void updateValues(List<Movie> elements) {
        synchronized (mLock) {
            this.elements = elements;
        }
        Log.e(TAG, "       *            *           *Dataset changed....");
        notifyDataSetChanged();
    }

    public void updateValuesNoNotify(List<Movie> elements) {
        synchronized (mLock) {
            this.elements = elements;
            Log.d(TAG, "------------------------- ELEMENTS UPDATED SUCCESSFULLY --------------");
        }
    }

    private class FetchMoviesData extends AsyncTask<Integer, Void, List<Movie>> {


        @Override
        protected List<Movie> doInBackground(Integer... params) {
            MovieDatabaseServerConnector connector = new MovieDatabaseServerConnector(context);
            Integer page = params[0];
            Log.d(TAG, "Retrieving values for page [" + page + "]");
            List<Movie> movies;
            try {
                movies = connector.getMovies(page, PAGE_SIZE, 0);
            } catch (IOException | UnauthorizedException | JSONException e) {
                Log.e(TAG, "Exception has been thrown: " + e.toString());
                // TODO: Display error message
                return new ArrayList<>();
            }

            return movies;
        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(context);
            pd.setTitle("Processing...");
            pd.setMessage("Please wait.");
            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();
            Log.d(TAG, "++++++++++++ showing waiting dialogue ++++++++++ ");
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            if (movies.size() == 0) {
                return;
            }
            updateValuesNoNotify(movies);
            if (pd != null) {
                Log.d(TAG, "----------- dismissing waiting dialogue -------");
                pd.dismiss();
            }
        }
    }
}
