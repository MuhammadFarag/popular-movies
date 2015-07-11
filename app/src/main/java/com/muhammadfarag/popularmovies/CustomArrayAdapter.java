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
    private Set<String> alreadyProcessedMovies = new HashSet<>();
    private boolean movingForward = true;


    public CustomArrayAdapter(Context context, int resource, List<Movie> elements) {
        this.context = context;
        this.resource = resource;
        this.currentView = elements;    // fill current view
        previousView = new ArrayList<>(currentView);     // fill previous view with data from current view
        nextView = new ArrayList<>();   // fill next view with new data
        FetchMoviesData fetchMoviesData = new FetchMoviesData();
        fetchMoviesData.execute(2);
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
        // Fixing issue when gridView randomly request view at position 0
        if (position == 0) {
            flag = true;
            position = (currentPage - 1) * pageSize;
            Log.w(TAG, "###### Adjusted position to: [" + position + "]");
        }
        if (position / pageSize + 1 > currentPage) {
            movingForward = true;
            currentPage = position / pageSize + 1;
            previousView = new ArrayList<>(currentView);
            currentView = new ArrayList<>(nextView);
            FetchMoviesData fetchMoviesData = new FetchMoviesData();
            fetchMoviesData.execute(currentPage + 1);
        }
        if (position / pageSize + 1 < currentPage) {
            movingForward = false;
            currentPage = position / pageSize + 1;
            currentView = new ArrayList<>(previousView);
            if (currentPage > 1) {
                FetchMoviesData fetchMoviesData = new FetchMoviesData();
                fetchMoviesData.execute(currentPage - 1);
            }
        }

        Log.d("PMD%%", "Current movie is " + currentView.get(position % pageSize).getOriginalTitle());
        if (alreadyProcessedMovies.contains(currentView.get(position % pageSize).getOriginalTitle()) && !flag) {
            Log.e(TAG, "This movie has been seen before: [" + currentView.get(position % pageSize).getOriginalTitle() + "]");
        }
        alreadyProcessedMovies.add(currentView.get(position % pageSize).getOriginalTitle());
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

    private class FetchMoviesData extends AsyncTask<Integer, Void, List<Movie>> {


        @Override
        protected List<Movie> doInBackground(Integer... params) {
            MovieDatabaseServerConnector connector = new MovieDatabaseServerConnector(context);
            List<Movie> movies;
            int pageNumber = params[0];
            try {
                movies = connector.getMovies(pageNumber, 20, 0);
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
            if (movingForward) {
                nextView = movies;
            } else {
                previousView = movies;
            }
        }
    }
}
