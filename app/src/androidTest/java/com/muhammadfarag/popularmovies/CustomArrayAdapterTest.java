package com.muhammadfarag.popularmovies;

import android.content.Context;
import android.test.AndroidTestCase;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

/**
 * Project: Popular Movies
 * Created by muhammad on 29/06/15.
 */
public class CustomArrayAdapterTest extends AndroidTestCase {

    private List<String> data;
    private CustomArrayAdapter arrayAdapter;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        data = Arrays.asList("Hello", "world");
        arrayAdapter = new CustomArrayAdapter(getContext(), R.layout.grid_view_cell, R.id.grid_view_cell, data);
    }

    public void testGetItem() {
        assertEquals("Hello", arrayAdapter.getItem(0));
    }

    public void testGetView() {
        View view = arrayAdapter.getView(0, null, null);
        assertTrue(view instanceof TextView);
    }


    private class CustomArrayAdapter extends ArrayAdapter<String> {
        public CustomArrayAdapter(Context context, int resource, int textViewResourceId, List<String> objects) {
            super(context, resource, textViewResourceId, objects);
        }
    }
}

