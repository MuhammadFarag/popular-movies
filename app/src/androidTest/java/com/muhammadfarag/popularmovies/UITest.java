package com.muhammadfarag.popularmovies;

import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;

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

}
