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
        assertEquals("Unexpected item returned", 99, data.get(99));
        assertEquals("Unexpected item returned", 100, data.get(100));
        assertEquals("Unexpected item returned", 199, data.get(199));
        assertEquals("Unexpected item returned", 200, data.get(200));
    }

    public void testMovingForwardAndBackwardPage() throws Exception {
        assertEquals("Unexpected item returned", 0, data.get(0));
        assertEquals("Unexpected item returned", 99, data.get(99));
        assertEquals("Unexpected item returned", 100, data.get(100));
        assertEquals("Unexpected item returned", 199, data.get(199));
        assertEquals("Unexpected item returned", 200, data.get(200));
        assertEquals("Unexpected item returned", 199, data.get(199));
        assertEquals("Unexpected item returned", 100, data.get(100));
        assertEquals("Unexpected item returned", 99, data.get(99));
        assertEquals("Unexpected item returned", 0, data.get(0));
    }

    public void testMovingForwardAndBackwardPageAndForward() throws Exception {
        assertEquals("Unexpected item returned", 0, data.get(0));
        assertEquals("Unexpected item returned", 99, data.get(99));
        assertEquals("Unexpected item returned", 100, data.get(100));
        assertEquals("Unexpected item returned", 199, data.get(199));
        assertEquals("Unexpected item returned", 200, data.get(200));
        assertEquals("Unexpected item returned", 199, data.get(199));
        assertEquals("Unexpected item returned", 100, data.get(100));
        assertEquals("Unexpected item returned", 99, data.get(99));
        assertEquals("Unexpected item returned", 0, data.get(0));
        assertEquals("Unexpected item returned", 99, data.get(99));
        assertEquals("Unexpected item returned", 100, data.get(100));
        assertEquals("Unexpected item returned", 199, data.get(199));
        assertEquals("Unexpected item returned", 200, data.get(200));
    }




    private class Data {
        List<Integer> previous = new ArrayList<>();
        List<Integer> current = new ArrayList<>();
        List<Integer> next = new ArrayList<>();
        private int currentPage = 0;
        private int moveBackThreashold = 0;

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

            if (i >= 100 * (currentPage + 1) && i < 100 * (currentPage + 2)) {
                currentPage += 1;
                moveBackThreashold = currentPage;
                Log.d(TAG, ">>>>>>>>> Moving forward ++++");
                previous = new ArrayList<>(current);
                current = new ArrayList<>(next);
                next = updateNext(currentPage);
            } else if (i < moveBackThreashold * 100 && currentPage == moveBackThreashold) {
                currentPage -= 1;
                moveBackThreashold = currentPage;
                Log.d(TAG, ">>>>>>>>> Moving backward ----- 1");
                next = new ArrayList<>(current);
                current = new ArrayList<>(previous);
                previous = updatePrevious(currentPage);
            }
            i = i % 100;
            return current.get(i);
        }

        public List<Integer> updateNext(int currentPage) {
            List<Integer> newPage = new ArrayList<>();
            int nextPage = currentPage + 1;
            new ArrayList<>();
            for (int j = 100 * nextPage; j < 100 * (nextPage + 1); j++) {
                newPage.add(j);
            }
            return newPage;
        }

        public List<Integer> updatePrevious(int currentPage) {
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
