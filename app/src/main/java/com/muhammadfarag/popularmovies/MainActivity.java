package com.muhammadfarag.popularmovies;

import android.app.ProgressDialog;
import android.content.DialogInterface;
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


public class MainActivity extends AppCompatActivity implements DataSetUpdateListener {

    private CustomArrayAdapter arrayAdapter;
    private int sortCriteria = 0; // sort by popularity
    private boolean showFavorites = false;

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
        List<Movie> movies = (List<Movie>) getLastCustomNonConfigurationInstance();
        if (movies == null) {
            FetchMoviesData fetchMoviesData = new FetchMoviesData(this);
            fetchMoviesData.execute();
        } else {
            arrayAdapter.updateValues(movies);
        }
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return arrayAdapter.getElements();
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
        if (id == R.id.action_sort) {
            showFavorites = false;
            if (this.sortCriteria == 0) {
                this.sortCriteria = 1;
                item.setTitle(R.string.action_sort_popularity);
            } else {
                this.sortCriteria = 0;
                item.setTitle(R.string.action_sort_user_rating);
            }
        } else if (id == R.id.action_favorites) {
            showFavorites = true;
        }
        FetchMoviesData fetchMoviesData = new FetchMoviesData(this);
        fetchMoviesData.execute();
        return true;
    }

    @Override
    public void onDataSetUpdated(List<Movie> movies) {
        arrayAdapter.updateValues(movies);
    }

    private class FetchMoviesData extends AsyncTask<Void, Void, List<Movie>> {

        private static final int PAGE_NUMBER_1 = 1;
        private ProgressDialog pd;
        private boolean unauthorizedExceptionOccured = false;
        private DataSetUpdateListener listener;

        public FetchMoviesData(DataSetUpdateListener listener) {
            this.listener = listener;
        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(MainActivity.this);
            pd.setTitle(getString(R.string.dialog_progress_title));
            pd.setMessage(getString(R.string.dialog_progress_message));
            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();
        }

        @Override
        protected List<Movie> doInBackground(Void... params) {
            MovieDatabaseServerConnector connector = new MovieDatabaseServerConnector(getApplicationContext());
            List<Movie> movies;
            if (showFavorites) {
                movies = FavoriteMoviesManager.create(MainActivity.this).getMovies();
            } else {
                try {
                    movies = connector.getMovies(PAGE_NUMBER_1, getResources().getInteger(R.integer.number_of_movies_to_load), sortCriteria);
                } catch (IOException | JSONException e) {
                    // TODO: Display error message
                    Log.e("", "Error occurred while parsing movies data...: " + e.toString());
                    return new ArrayList<>();
                } catch (UnauthorizedException e) {
                    unauthorizedExceptionOccured = true;
                    return new ArrayList<>();
                }
            }

            return movies;
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            if (unauthorizedExceptionOccured) {
                final EditText apiKeyEditText = new EditText(MainActivity.this);

                apiKeyEditText.setHint(getString(R.string.dialog_authorization_hint));

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(getString(R.string.dialog_authorization_title))
                        .setMessage(getString(R.string.dialog_authorization_body))
                        .setView(apiKeyEditText)
                        .setPositiveButton(getString(R.string.dialog_authorization_ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String apiKey = apiKeyEditText.getText().toString();
                                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("api-key", apiKey).commit();
                                editor.commit();
                                finish();
                                startActivity(getIntent());
                            }
                        })
                        .setNegativeButton(getString(R.string.dialog_authorization_cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {
                                finish();
                                startActivity(getIntent());
                            }
                        })
                        .show();
            }
            listener.onDataSetUpdated(movies);
            if (pd != null) {
                pd.dismiss();
            }
        }
    }


}
