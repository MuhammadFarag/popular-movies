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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Project: Popular Movies
 * Created by muhammad on 01/07/15.
 */
class CustomArrayAdapter extends BaseAdapter {

    private static final String TAG = "PMD%";
    private final Context context;
    private final int resource;
    private List<Movie> previousView;
    private List<Movie> currentView;
    private List<Movie> nextView;
    private final Object mLock = new Object();
    private int currentPage = 1;
    private int pageSize = 20;
    private boolean firstCountCheck = true;
    private Set<String> aleadyProcessedMovies = new HashSet<>();

    public CustomArrayAdapter(Context context, int resource, List<Movie> elements) {
        this.context = context;
        this.resource = resource;
        this.currentView = elements;
        previousView = new ArrayList<>();
        nextView = new ArrayList<>();
        FetchMoviesData fetchMoviesData = new FetchMoviesData();
        fetchMoviesData.execute();
    }

    @Override
    public int getCount() {
        if (currentView.size() == 0 && firstCountCheck) {
            return 0;
        } else {
            firstCountCheck = false;
            return 20000;
        }
    }

    @Override
    public Movie getItem(int position) {
        Log.d("PMD%%", "Current position is " + position);
        boolean flag = false;
        if (position == 0) {
            flag = true;
            position = (currentPage - 1) * pageSize;
            Log.w(TAG, "###### Adjusted position to: [" + position + "]");
        }
        if (position / pageSize + 1 != currentPage) {
            currentPage = position / pageSize + 1;
            currentView = new ArrayList<>(nextView);
            FetchMoviesData fetchMoviesData = new FetchMoviesData();
            fetchMoviesData.execute();
        }
        Log.d("PMD%%", "Current movie is " + currentView.get(position % pageSize).getOriginalTitle());
        if (aleadyProcessedMovies.contains(currentView.get(position % pageSize).getOriginalTitle()) && !flag) {
            Log.e(TAG, "This movie has been seen before: [" + currentView.get(position % pageSize).getOriginalTitle() + "]");
        }
        aleadyProcessedMovies.add(currentView.get(position % pageSize).getOriginalTitle());
        return currentView.get(position % pageSize);
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
            this.currentView = elements;
        }
        notifyDataSetChanged();
    }

    private class FetchMoviesData extends AsyncTask<Void, Void, List<Movie>> {

        @Override
        protected List<Movie> doInBackground(Void... params) {
            MovieDatabaseServerConnector connector = new MovieDatabaseServerConnector(context);
            List<Movie> movies;
            try {
                movies = connector.getMovies(currentPage + 1, 20, 0);
                Log.d(TAG, ">>>>>>>>> loading next page [" + (currentPage + 1) + "]");
            } catch (IOException | UnauthorizedException | JSONException e) {
                Log.e(TAG, "Error occurred" + e.toString());
                // TODO: Display error message
                return new ArrayList<>();
            }

            return movies;
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {

            nextView = movies;
        }
    }
}
