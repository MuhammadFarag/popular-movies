package com.muhammadfarag.popularmovies.details.reviews;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.AdapterView;

import com.muhammadfarag.popularmovies.details.FetchMovieElements;
import com.muhammadfarag.popularmovies.details.MovieElementsAdapter;
import com.muhammadfarag.popularmovies.details.MovieElementsFragment;

import java.util.HashMap;

/**
 * Created by muhammadfarag on 10/3/15.
 */
public class ReviewsFragment extends MovieElementsFragment {


    public static ReviewsFragment newInstance(int id) {
        ReviewsFragment fragment = new ReviewsFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(KEY_ID, id);
        fragment.setArguments(arguments);
        return fragment;
    }


    @NonNull
    @Override
    protected FetchMovieElements setMovieElementsFetcher() {
        return new FetchReviews(getActivity(), this);
    }

    @NonNull
    @Override
    protected MovieElementsAdapter setArrayAdapter() {
        return new ReviewsArrayAdapter(getActivity(), android.R.layout.simple_list_item_2, new HashMap<String, String>());
    }


    @NonNull
    @Override
    protected AdapterView.OnItemClickListener setonItemClickListener() {
        return null;
    }

}
