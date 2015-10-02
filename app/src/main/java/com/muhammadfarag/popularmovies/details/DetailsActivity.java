package com.muhammadfarag.popularmovies.details;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.muhammadfarag.popularmovies.R;


public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

}
