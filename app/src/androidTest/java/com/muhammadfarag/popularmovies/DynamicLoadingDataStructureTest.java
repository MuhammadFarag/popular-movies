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
    private Data data;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        data = new Data();
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


    private class Data {
        List<Integer> previous = new ArrayList<>();
        List<Integer> current = new ArrayList<>();
        List<Integer> next = new ArrayList<>();
        private int currentPage = 0;
        private int moveBackThreshold = 0;
        private boolean updateNext = false;
        private boolean updatePrevious = false;

        public Data() {
            for (int i = 0; i < 100; i++) {
                current.add(i);
            }
            for (int i = 100; i < 200; i++) {
                next.add(i);
            }
        }

        public int get(int i) {
            Log.d(TAG, ">>>>>>>>> The value of i = [" + i + "] page = [" + currentPage + "] current = [" + current.get(0) + "... ]");
            Log.d(TAG,"####### The value of i%100 [" + i%100 +"]");
            if (i % 100 == 50) {
                if (updateNext) {
                    next = updateNext(currentPage);
                    updateNext = false;
                }
                if (updatePrevious) {
                    previous = updatePrevious(currentPage);
                    updatePrevious = false;
                }
            }

            if (i >= 100 * (currentPage + 1) && i < 100 * (currentPage + 2)) {
                currentPage += 1;
                moveBackThreshold = currentPage;
                Log.d(TAG, ">>>>>>>>> Moving forward ++++");
                previous = new ArrayList<>(current);
                current = new ArrayList<>(next);
                updateNext = true;
                updatePrevious = false;
            } else if (i < moveBackThreshold * 100 && currentPage == moveBackThreshold) {
                currentPage -= 1;
                moveBackThreshold = currentPage;
                Log.d(TAG, ">>>>>>>>> Moving backward ----- 1");
                next = new ArrayList<>(current);
                current = new ArrayList<>(previous);
                updateNext = false;
                updatePrevious = true;
            }

            i = i % 100;
            return current.get(i);
        }

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
