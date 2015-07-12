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

    private final Context context;
    private final int resource;
    private List<Movie> elements;
    private final Object mLock = new Object();
    private MoviesDataSet movies = new MoviesDataSet();
    private ProgressDialog pd;
    private boolean enableDialogue = false;
    Movie movie0 = null;



    public CustomArrayAdapter(Context context, int resource, List<Movie> elements) {
        Log.d(">>     >>    >>", "custom array adapter loaded");
        this.context = context;
        this.resource = resource;
        this.elements = elements;
    }

    @Override
    public int getCount() {
        Log.d(">>     >>    >>", "get count loaded");
        if (movies.setupCompleted()) {
            Log.d(">>     >>    >>", "get count returned 2000");
            enableDialogue = true;
            return 2000;
        } else {
            Log.d(">>     >>    >>", "get count returned 0");
            return 0;
        }
//        return elements.size();
    }

    @Override
    public Movie getItem(int position) {
        if(position == 0){
            if(movie0==null){
                movie0 = movies.get(position);
                return movie0;
            }
        }
        return movies.get(position);
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
        notifyDataSetChanged();
        if(pd!=null){
            pd.dismiss();
        }

    }

    private class MoviesDataSet extends DataSet<Movie> {


        public void setup() {
            previous = new ArrayList<>();
            updateNext(2);
            updatePrevious(1);
        }

        @Override
        void updateNext(int currentPage) {
            FetchMoviesData fetchMoviesData = new FetchMoviesData(true);
            fetchMoviesData.execute(currentPage);
        }

        @Override
        void updatePrevious(int currentPage) {
            FetchMoviesData fetchMoviesData = new FetchMoviesData(false);
            fetchMoviesData.execute(currentPage);
        }

        @Override
        public boolean setupCompleted() {
            if (previous.size() > 0) {
                current = new ArrayList<>(previous);
            }
            return super.setupCompleted();
        }

        @Override
        public void valueUpdated() {
            Log.d(">>     >>    >>", "value updated notification called");
//            notifyDataSetChanged();
        }
    }


    private class FetchMoviesData extends AsyncTask<Integer, Void, List<Movie>> {
        private boolean updateNext;

        public FetchMoviesData(boolean updateNext) {
            Log.d(">>     >>    >>", "FetchMoviesData called - updateNext = " + updateNext);
            this.updateNext = updateNext;
        }

        @Override
        protected List<Movie> doInBackground(Integer... params) {
            MovieDatabaseServerConnector connector = new MovieDatabaseServerConnector(context);
            List<Movie> movies;
            int page = params[0];
            Log.d(">>     >>    >>","Retrieving page number: " + page);
            try {
                movies = connector.getMovies(page, 100, 0);
            } catch (IOException | UnauthorizedException | JSONException e) {
                Log.e(">>     >>    >>", "Error has occured while attempting to retrieve data: " + e.toString());
                return new ArrayList<>();
            }

            return movies;
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            Log.d(">>     >>    >>", "Data retrieval executed with updateNext: " + updateNext);
            if (updateNext) {
                CustomArrayAdapter.this.movies.setNext(movies);
                Log.d("777777 ", "Next has been set with first element to be: " + movies.get(0));
            } else {
                CustomArrayAdapter.this.movies.setPrevious(movies);
                Log.d("666666 ", "Previous has been set with first element to be: " + movies.get(0));

            }
//            updateValues(movies);
        }

        @Override
        protected void onPreExecute() {
//            if(CustomArrayAdapter.this.movies.setupCompleted()){
//
            if (enableDialogue) {
                pd = new ProgressDialog(context);
                pd.setTitle("Processing...");
                pd.setMessage("Please wait.");
                pd.setCancelable(false);
                pd.setIndeterminate(true);
                pd.show();
                Log.d(">>     >>    >>", "++++++++++++ showing waiting dialogue ++++++++++ ");
            }
//            }
        }
    }
}
