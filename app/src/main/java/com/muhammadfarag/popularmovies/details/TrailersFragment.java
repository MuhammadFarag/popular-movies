package com.muhammadfarag.popularmovies.details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.muhammadfarag.popularmovies.R;

/**
 * Created by muhammadfarag on 10/3/15.
 */
public class TrailersFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trailers, container, false);
        return view;
    }
}
