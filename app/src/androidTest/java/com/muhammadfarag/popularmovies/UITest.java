package com.muhammadfarag.popularmovies;

import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

/**
 * Project: Popular Movies
 * Created by muhammad on 28/06/15.
 */
public class UITest extends ActivityInstrumentationTestCase2<MainActivity> {


    public UITest() {
        super(MainActivity.class);
    }

    public void testUIShouldHaveGridView() {
        final View decorView = getActivity().getWindow().getDecorView();
        GridView gridViewLayout = (GridView) getActivity().findViewById(R.id.grid_view_layout);
        ViewAsserts.assertOnScreen(decorView, gridViewLayout);

        final ViewGroup.LayoutParams layoutParams =
                gridViewLayout.getLayoutParams();
        assertNotNull(layoutParams);
        assertEquals("Layout width", layoutParams.width, WindowManager.LayoutParams.MATCH_PARENT);
        assertEquals("Layout height", layoutParams.height, WindowManager.LayoutParams.MATCH_PARENT);
    }

    public void testSimpleArrayAdapter() {
        List<String> data = Arrays.asList("Hello", "world");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.grid_view_cell, R.id.grid_view_cell, data);
        assertEquals("Hello",arrayAdapter.getItem(0));
        View view = arrayAdapter.getView(0, null, null);
        assertTrue(view instanceof TextView);

    }

}
