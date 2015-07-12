package com.muhammadfarag.popularmovies;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Project: Popular Movies
 * Created by muhammad on 12/07/15.
 */
public abstract class DataSet<T> {
    private static final String TAG = "ZolaPora";
    private static final int PAGE_SIZE_100 = 100;

    List<T> previous = new ArrayList<>();
    List<T> current = new ArrayList<>();
    List<T> next = new ArrayList<>();
    private int currentPage = 0;
    private int moveBackThreshold = 0;
    private boolean updateNext = false;
    private boolean updatePrevious = false;

    public DataSet() {
        setup();
    }

    abstract void setup();

    public boolean setupCompleted() {
        return previous.size() == PAGE_SIZE_100 && previous.size() == PAGE_SIZE_100 && previous.size() == PAGE_SIZE_100;
    }

    public T get(int i) {
        if (i != 0) {
            Log.d(TAG, ">>>>>>>>> The value of i = [" + i + "] page = [" + currentPage + "] current = [" + current.get(0) + "... ]");
            Log.d(TAG, "####### The value of i%100 [" + i % PAGE_SIZE_100 + "]");
        }
        if (i % PAGE_SIZE_100 == PAGE_SIZE_100 / 2) {
            if (updateNext) {
                updateNext(currentPage);
                updateNext = false;
            }
            if (updatePrevious) {
                updatePrevious(currentPage);
                updatePrevious = false;
            }
        }

        if (i >= PAGE_SIZE_100 * (currentPage + 1) && i < PAGE_SIZE_100 * (currentPage + 2)) {
            currentPage += 1;
            moveBackThreshold = currentPage;
            Log.d(TAG, ">>>>>>>>> Moving forward ++++Page =" + currentPage);
            previous = new ArrayList<>(current);
            current = new ArrayList<>(next);
            updateNext = true;
            updatePrevious = false;
        } else if (i < moveBackThreshold * PAGE_SIZE_100 && currentPage == moveBackThreshold) {
            currentPage -= 1;
            moveBackThreshold = currentPage;
            Log.d(TAG, ">>>>>>>>> Moving backward ----- Page =" + currentPage);
            next = new ArrayList<>(current);
            current = new ArrayList<>(previous);
            updateNext = false;
            updatePrevious = true;
        }

        i = i % PAGE_SIZE_100;
        return current.get(i);
    }

    public void setNext(List<T> next) {
        this.next = next;
        valueUpdated();
    }

    public void setPrevious(List<T> previous) {
        this.previous = previous;
        valueUpdated();
    }

    abstract void updateNext(int currentPage);

    abstract void updatePrevious(int currentPage);

    public void valueUpdated() {

    }
}
