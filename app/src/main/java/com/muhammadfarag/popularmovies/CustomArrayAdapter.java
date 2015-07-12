package com.muhammadfarag.popularmovies;

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

    private final Context context;
    private final int resource;
    private List<Movie> elements;
    private final Object mLock = new Object();
    private int currentPage =1;

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
        Log.d("PopMovieApp", "Getting item from position[" + position +
                "] with current page number [" + currentPage +
                "]");
        if(currentPage<position/20 + 1){
            currentPage = position /20 + 1;
            Log.d("PopMovieApp", "Increment page number to [" + currentPage +
                    "]");
            FetchMoviesData fetchMoviesData = new FetchMoviesData();
            fetchMoviesData.execute(currentPage);
        }
        if(position !=0 && currentPage>position/20 + 1){
            currentPage = position /20+1;
            Log.d("PopMovieApp", "Decrement page number to [" + currentPage +
                    "]");
            FetchMoviesData fetchMoviesData = new FetchMoviesData();
            fetchMoviesData.execute(currentPage);
        }
        return elements.get(position%20);
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

        String url = getItem(position).getPosterUrl();
        Picasso.with(context).load(url).into(view);
//        Picasso.with(context).load(albumURL).error(R.drawable.no_album_art).into(holder.cell_image);


//        view.setImageResource(R.drawable.abc_btn_check_to_on_mtrl_000);
        return view;
    }

    public void updateValues(List<Movie> elements) {
        synchronized (mLock) {
            this.elements = elements;
        }
        Log.e("PopMovies", "       *            *           *Dataset changed....");
        notifyDataSetChanged();
    }
    public void updateValuesNoNotify(List<Movie> elements) {
        synchronized (mLock) {
            this.elements = elements;
        }
    }

    private class FetchMoviesData extends AsyncTask<Integer, Void, List<Movie>> {

        @Override
        protected List<Movie> doInBackground(Integer... params) {
            MovieDatabaseServerConnector connector = new MovieDatabaseServerConnector(context);
            Integer page = params[0];
            Log.d("PopMovies", "Retrieving values for page [" + page +"]");
            List<Movie> movies;
            try {
                movies = connector.getMovies(page, 20, 0);
            } catch (IOException | UnauthorizedException | JSONException e) {
                Log.e("PopMovieApp","Exception has been thrown: " + e.toString() );
                // TODO: Display error message
                return new ArrayList<>();
            }

            return movies;
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            if(movies.size()==0){
                return;
            }
            updateValuesNoNotify(movies);
        }
    }
}
