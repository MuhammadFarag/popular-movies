package com.muhammadfarag.popularmovies;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private CustomArrayAdapter arrayAdapter;
    private int sortCriteria = 0; // sort by popularity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<Movie> elements = new ArrayList<>();
        arrayAdapter = new CustomArrayAdapter(getApplicationContext(), R.layout.grid_view_cell, elements);
        GridView gridViewLayout = (GridView) findViewById(R.id.grid_view_layout);
        gridViewLayout.setAdapter(arrayAdapter);
        gridViewLayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = (Movie) parent.getAdapter().getItem(position);
                Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                intent.putExtra("movie", movie);
                startActivity(intent);
            }
        });
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
        if (id == R.id.action_sorting) {
            if (this.sortCriteria == 0) {
                this.sortCriteria = 1;
                item.setTitle("Sort By Popularity");
            } else {
                this.sortCriteria = 0;
                item.setTitle("Sort By User Rating");
            }
            FetchMoviesData fetchMoviesData = new FetchMoviesData();
            fetchMoviesData.execute();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class FetchMoviesData extends AsyncTask<Void, Void, List<Movie>> {

        private ProgressDialog pd;
        private boolean unauthorizedExceptionOccured = false;

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(MainActivity.this);
            pd.setTitle("Loading Movies...");
            pd.setMessage("Please wait.");
            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();
            Log.d(">>     >>    >>", "++++++++++++ showing waiting dialogue ++++++++++ ");
        }

        @Override
        protected List<Movie> doInBackground(Void... params) {
            MovieDatabaseServerConnector connector = new MovieDatabaseServerConnector(getApplicationContext());
            List<Movie> movies;
            try {
                movies = connector.getMovies(1, 500, sortCriteria);
            } catch (IOException | JSONException e) {
                // TODO: Display error message
                return new ArrayList<>();
            } catch (UnauthorizedException e) {
                unauthorizedExceptionOccured = true;
                // TODO: Display error message
                return new ArrayList<>();
            }

            return movies;
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            if (unauthorizedExceptionOccured) {
                final EditText apiKeyEditText = new EditText(MainActivity.this);

                apiKeyEditText.setHint("Api-key as provided by themoviedb.org");

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Authorization Error Occurred")
                        .setMessage("Please paste the api-key for themoviedb.org, or check project readme and set it programmatically!\nCancel will retry the current key")
                        .setView(apiKeyEditText)
                        .setPositiveButton("Ok", (dialog, whichButton) -> {
                            String apiKey = apiKeyEditText.getText().toString();
                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("api-key", apiKey);
                            editor.commit();
                            finish();
                            startActivity(getIntent());
                        })
                        .setNegativeButton("Cancel", (dialog, whichButton) -> {
                            finish();
                            startActivity(getIntent());
                        })
                        .show();
            }
            arrayAdapter.updateValues(movies);
            if (pd != null) {
                pd.dismiss();
            }
        }
    }


}
