package com.muhammadfarag.popularmovies.details.reviews;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.muhammadfarag.popularmovies.R;
import com.muhammadfarag.popularmovies.details.DataSetUpdateListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by muhammadfarag on 10/3/15.
 */
public class ReviewsFragment extends Fragment implements DataSetUpdateListener {

    public static final String KEY_ID = "id";
    private ReviewsArrayAdapter arrayAdapter;

    public static ReviewsFragment newInstance(int id) {
        ReviewsFragment fragment = new ReviewsFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(KEY_ID, id);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arrayAdapter = new ReviewsArrayAdapter(getActivity(), android.R.layout.simple_list_item_2, new HashMap<String, String>());
        Map<String, String> elements = null;
//        if (savedInstanceState != null) {
//            elements = savedInstanceState.getParcelableArrayList("");
//        }
//        if (elements == null) {
        FetchReviews fetchReviews = new FetchReviews(getActivity(), this);
        fetchReviews.execute(getArguments().getInt(KEY_ID));
//        } else {
//            arrayAdapter.updateValues(elements);
//        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        ListView gridViewLayout = (ListView) view.findViewById(R.id.trailer_list_view);
        gridViewLayout.setAdapter(arrayAdapter);
        gridViewLayout.setEmptyView(view.findViewById(R.id.empty));
        view.setBackgroundColor(getResources().getColor(R.color.windowBackground));
        return view;
    }

    @Override
    public void onDataSetUpdated(Map<String, String> trailers) {
        arrayAdapter.updateValues(trailers);
    }
}
