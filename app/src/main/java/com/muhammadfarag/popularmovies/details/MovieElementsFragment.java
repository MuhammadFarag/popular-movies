package com.muhammadfarag.popularmovies.details;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.muhammadfarag.popularmovies.R;
import com.muhammadfarag.popularmovies.details.DataSetUpdateListener;
import com.muhammadfarag.popularmovies.details.FetchMovieElements;
import com.muhammadfarag.popularmovies.details.MovieElementsAdapter;

import java.util.Map;

/**
 * Created by muhammadfarag on 10/4/15.
 */
public abstract class MovieElementsFragment extends Fragment implements DataSetUpdateListener {
    public static final String KEY_ID = "id";
    protected MovieElementsAdapter arrayAdapter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arrayAdapter = setArrayAdapter();

        FetchMovieElements fetchTrailers = setMovieElementsFetcher();
        fetchTrailers.execute(getArguments().getInt(KEY_ID));
    }

    @NonNull
    protected abstract FetchMovieElements setMovieElementsFetcher();

    @NonNull
    protected abstract MovieElementsAdapter setArrayAdapter();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        ListView gridViewLayout = (ListView) view.findViewById(R.id.trailer_list_view);
        gridViewLayout.setAdapter(arrayAdapter);
        gridViewLayout.setEmptyView(view.findViewById(R.id.empty));
        gridViewLayout.setOnItemClickListener(setonItemClickListener());
        view.setBackgroundColor(getResources().getColor(R.color.windowBackground));

        return view;
    }

    @NonNull
    protected abstract AdapterView.OnItemClickListener setonItemClickListener();

    @Override
    public void onDataSetUpdated(Map<String, String> trailers) {
        arrayAdapter.updateValues(trailers);
    }
}
