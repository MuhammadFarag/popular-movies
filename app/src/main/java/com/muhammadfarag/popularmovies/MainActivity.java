package com.muhammadfarag.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements DataSetUpdateListener {

    public static final String KEY_MOVIES = "key_movies";
    private CustomArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arrayAdapter = new CustomArrayAdapter(this, R.layout.grid_view_cell, new ArrayList<Movie>());
        GridView gridViewLayout = (GridView) findViewById(R.id.grid_view_layout);
        gridViewLayout.setAdapter(arrayAdapter);
        gridViewLayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = (Movie) parent.getAdapter().getItem(position);
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("movie", movie);
                startActivity(intent);
            }
        });
        List<Movie> movies = null;
        if (savedInstanceState != null) {
            movies = savedInstanceState.getParcelableArrayList(KEY_MOVIES);
        }

        if (movies == null) {
            FetchMoviesData fetchMoviesData = new FetchMoviesData(this, this);
            fetchMoviesData.execute();
        } else {
            arrayAdapter.updateValues(movies);
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY_MOVIES, (ArrayList<? extends Parcelable>) arrayAdapter.getElements());

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
        if (id == R.id.action_sort_user_rating) {
            FetchMoviesData fetchMoviesData = new FetchMoviesData(this, this, 1);
            fetchMoviesData.execute();
        } else if (id == R.id.action_sort_popularity) {
            FetchMoviesData fetchMoviesData = new FetchMoviesData(this, this, 0);
            fetchMoviesData.execute();

        } else if (id == R.id.action_favorites) {
            onDataSetUpdated(FavoriteMoviesManager.create(MainActivity.this).getMovies());
        }
        return true;
    }

    @Override
    public void onDataSetUpdated(List<Movie> movies) {
        arrayAdapter.updateValues(movies);
    }


}
