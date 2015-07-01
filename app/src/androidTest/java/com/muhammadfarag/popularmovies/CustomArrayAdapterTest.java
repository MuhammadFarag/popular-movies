package com.muhammadfarag.popularmovies;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.view.View;
import android.widget.ImageView;

import java.util.Arrays;
import java.util.List;

/**
 * Project: Popular Movies
 * Created by muhammad on 29/06/15.
 */
public class CustomArrayAdapterTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private List<String> data;
    private CustomArrayAdapter arrayAdapter;

    public CustomArrayAdapterTest() {
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        data = Arrays.asList("Hello", "world");
        arrayAdapter = new CustomArrayAdapter(getActivity(), R.layout.grid_view_cell, data);
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

    @UiThreadTest
    public void testGetViewReturnsAnImageView() {
        View view = arrayAdapter.getView(0, null, null);
        assertNotNull("Required view is null", view);
        String assertionText = "Expected ImageView but found: " + view.getClass().getSimpleName();
        assertTrue(assertionText, view instanceof ImageView);
    }

    //TODO: Testing to ensure that image view has
//    @UiThreadTest
//    public void testAnImageIsLoadedInImageView() {
//        ImageView view = (ImageView) arrayAdapter.getView(0, null, null);
//        assertNotNull("Required view is null", view);
//        assertNotNull("No image has been loaded in ImageView", view.getDrawable());
//    }

}

