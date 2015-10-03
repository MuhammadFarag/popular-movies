package com.muhammadfarag.popularmovies.details;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.muhammadfarag.popularmovies.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by muhammadfarag on 10/3/15.
 */
public class TrailersFragment extends Fragment implements TrailersDataSetUpdateListener {

    private TrailersArrayAdapter arrayAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arrayAdapter = new TrailersArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, new HashMap<String, String>());

        Map<String, String> elements = null;
//        if (savedInstanceState != null) {
//            elements = savedInstanceState.getParcelableArrayList("");
//        }
        if (elements == null) {
            FetchTrailers fetchTrailers = new FetchTrailers(getActivity(), this);
            fetchTrailers.execute(218);
        } else {
            arrayAdapter.updateValues(elements);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trailers, container, false);

        ListView gridViewLayout = (ListView) view.findViewById(R.id.trailer_list_view);
        gridViewLayout.setAdapter(arrayAdapter);
        gridViewLayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("tag", "Trailer was selected but nothing to be done ");
                Log.d("tag", "Trailer to Display: " + "http://www.youtube.com/watch?v=" + arrayAdapter.getItem(position));
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + arrayAdapter.getItem(position))));
            }
        });

        return view;
    }

    @Override
    public void onDataSetUpdated(Map<String, String> trailers) {
        arrayAdapter.updateValues(trailers);
    }
}
