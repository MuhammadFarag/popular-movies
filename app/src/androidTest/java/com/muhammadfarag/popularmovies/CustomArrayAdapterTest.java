package com.muhammadfarag.popularmovies;

import android.content.Context;
import android.test.AndroidTestCase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

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
        arrayAdapter = new CustomArrayAdapter(getContext(), R.layout.grid_view_cell, data);
    }

    public void testGetItem() {
        assertEquals("Hello", arrayAdapter.getItem(0));
    }

    public void testGetCount() {
        assertEquals(data.size(), arrayAdapter.getCount());
    }

    public void testGetId() {
        assertEquals(0, arrayAdapter.getItemId(0));
    }

    public void testGetViewReturnsAnImageView() {
        View view = arrayAdapter.getView(0, null, null);
        assertNotNull("Required view is null", view);
        String assertionText = "Expected ImageView but found: " + view.getClass().getSimpleName();
        assertTrue(assertionText, view instanceof ImageView);
    }

    public void testAnImageIsLoadedInImageView() throws InterruptedException {
        ImageView view = (ImageView) arrayAdapter.getView(0, null, null);
        assertNotNull("Required view is null", view);
        assertNotNull("No image has been loaded in ImageView", view.getDrawable());
    }

    private class CustomArrayAdapter extends BaseAdapter {

        private final Context context;
        private final int resource;
        private final List<String> elements;

        public CustomArrayAdapter(Context context, int resource, List<String> elements) {
            this.context = context;
            this.resource = resource;
            this.elements = elements;
        }

        @Override
        public int getCount() {
            return elements.size();
        }

        @Override
        public Object getItem(int position) {
            return elements.get(position);
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

//            String url = getItem(position);

            view.setImageResource(R.drawable.abc_btn_check_to_on_mtrl_000);
            return view;
        }
    }
}

