package com.muhammadfarag.popularmovies;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private CustomArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<String> elements = new ArrayList<>();
        arrayAdapter = new CustomArrayAdapter(getApplicationContext(), R.layout.grid_view_cell, elements);
        GridView gridViewLayout = (GridView) findViewById(R.id.grid_view_layout);
        gridViewLayout.setAdapter(arrayAdapter);
        FetchMoviesData fetchMoviesData = new FetchMoviesData();
        fetchMoviesData.execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class FetchMoviesData extends AsyncTask<Void, Void, List<String>> {

        @Override
        protected List<String> doInBackground(Void... params) {
            MovieDatabaseServerConnector connector = new MovieDatabaseServerConnector(getApplicationContext());
            List<Movie> movies;
            List<String> urls = new ArrayList<>();
            try {
                movies = connector.getMovies(1, 60);
            } catch (IOException | UnauthorizedException | JSONException e) {
                // TODO: Display error message
                return urls;
            }

            for (int i = 0; i < movies.size(); i++) {
                urls.add(movies.get(i).getPosterUrl());
            }
            return urls;
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            arrayAdapter.updateValues(strings);
        }
    }


}
