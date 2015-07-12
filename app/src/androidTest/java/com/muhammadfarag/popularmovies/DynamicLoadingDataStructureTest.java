package com.muhammadfarag.popularmovies;

import android.util.Log;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Project: Popular Movies
 * Created by muhammad on 12/07/15.
 */
public class DynamicLoadingDataStructureTest extends TestCase {

    private static final String TAG = "ZolaPora";
    private TestDataSet data;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        data = new TestDataSet();
    }

    public void testMovingForward() throws Exception {
        assertEquals("Unexpected item returned", 0, data.get(0));
        assertEquals("Unexpected item returned", 50, data.get(50));
        assertEquals("Unexpected item returned", 99, data.get(99));
        assertEquals("Unexpected item returned", 100, data.get(100));
        assertEquals("Unexpected item returned", 150, data.get(150));
        assertEquals("Unexpected item returned", 199, data.get(199));
        assertEquals("Unexpected item returned", 200, data.get(200));
    }

    public void testMovingForwardAndBackwardPage() throws Exception {
        assertEquals("Unexpected item returned", 0, data.get(0));
        assertEquals("Unexpected item returned", 50, data.get(50));
        assertEquals("Unexpected item returned", 99, data.get(99));
        assertEquals("Unexpected item returned", 100, data.get(100));
        assertEquals("Unexpected item returned", 150, data.get(150));
        assertEquals("Unexpected item returned", 199, data.get(199));
        assertEquals("Unexpected item returned", 200, data.get(200));
        assertEquals("Unexpected item returned", 150, data.get(150));
        assertEquals("Unexpected item returned", 199, data.get(199));
        assertEquals("Unexpected item returned", 100, data.get(100));
        assertEquals("Unexpected item returned", 50, data.get(50));
        assertEquals("Unexpected item returned", 99, data.get(99));
        assertEquals("Unexpected item returned", 0, data.get(0));
    }

    public void testMovingForwardAndBackwardPageAndForward() throws Exception {
        assertEquals("Unexpected item returned", 0, data.get(0));
        assertEquals("Unexpected item returned", 50, data.get(50));
        assertEquals("Unexpected item returned", 99, data.get(99));
        assertEquals("Unexpected item returned", 100, data.get(100));
        assertEquals("Unexpected item returned", 150, data.get(150));
        assertEquals("Unexpected item returned", 199, data.get(199));
        assertEquals("Unexpected item returned", 200, data.get(200));
        assertEquals("Unexpected item returned", 150, data.get(150));
        assertEquals("Unexpected item returned", 199, data.get(199));
        assertEquals("Unexpected item returned", 100, data.get(100));
        assertEquals("Unexpected item returned", 50, data.get(50));
        assertEquals("Unexpected item returned", 99, data.get(99));
        assertEquals("Unexpected item returned", 0, data.get(0));
        assertEquals("Unexpected item returned", 50, data.get(50));
        assertEquals("Unexpected item returned", 99, data.get(99));
        assertEquals("Unexpected item returned", 100, data.get(100));
        assertEquals("Unexpected item returned", 150, data.get(150));
        assertEquals("Unexpected item returned", 199, data.get(199));
        assertEquals("Unexpected item returned", 200, data.get(200));
    }

    /**
     * Tested by examining logs (TODO: Better figure automated way for testing)
     */
    public void testFluctuationDoesNotResultInUpdateRequests() {
        assertEquals("Unexpected item returned", 99, data.get(99));
        assertEquals("Unexpected item returned", 100, data.get(100));
        assertEquals("Unexpected item returned", 99, data.get(99));
        assertEquals("Unexpected item returned", 100, data.get(100));
        assertEquals("Unexpected item returned", 99, data.get(99));
        assertEquals("Unexpected item returned", 100, data.get(100));
        assertEquals("Unexpected item returned", 99, data.get(99));
        assertEquals("Unexpected item returned", 100, data.get(100));
        assertEquals("Unexpected item returned", 99, data.get(99));
        assertEquals("Unexpected item returned", 100, data.get(100));
        assertEquals("Unexpected item returned", 99, data.get(99));
        assertEquals("Unexpected item returned", 100, data.get(100));
        assertEquals("Unexpected item returned", 99, data.get(99));
    }




    private class TestDataSet extends DataSet {

        public TestDataSet() {
            for (int i = 0; i < 100; i++) {
                current.add(i);
            }
            for (int i = 100; i < 200; i++) {
                next.add(i);
            }
        }

        @Override
        public List<Integer> updateNext(int currentPage) {
            Log.d(TAG, "Updating next");
            List<Integer> newPage = new ArrayList<>();
            int nextPage = currentPage + 1;
            new ArrayList<>();
            for (int j = 100 * nextPage; j < 100 * (nextPage + 1); j++) {
                newPage.add(j);
            }
            return newPage;
        }

        @Override
        public List<Integer> updatePrevious(int currentPage) {
            Log.d(TAG, "Updating previous");
            List<Integer> newPage = new ArrayList<>();
            int nextPage = currentPage - 1;
            new ArrayList<>();
            for (int j = 100 * nextPage; j < 100 * (nextPage + 1); j++) {
                newPage.add(j);
            }
            return newPage;
        }
    }
}
