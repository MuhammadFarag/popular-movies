package com.muhammadfarag.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


/**
 * A placeholder fragment containing a simple view.
 */
public class DetailsActivityFragment extends Fragment {

    public DetailsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra("movie")) {
            Movie movie = (Movie) intent.getSerializableExtra("movie");

            TextView moviePlot = (TextView) rootView.findViewById(R.id.movie_plot);
            moviePlot.setText(movie.getPlotSynopsis());

            TextView movieTitle = (TextView) rootView.findViewById(R.id.movie_title);
            movieTitle.setText(movie.getOriginalTitle());

            TextView rating = (TextView) rootView.findViewById(R.id.movie_rating);
            rating.setText(String.valueOf(movie.getUserRating()));

            TextView releaseDate = (TextView) rootView.findViewById(R.id.movie_release_date);
            releaseDate.setText(movie.getReleaseDate());

            ImageView imageView = (ImageView) rootView.findViewById(R.id.movie_poster);
            Picasso.with(getActivity()).load(movie.getPosterUrl()).into(imageView);

        }
        return rootView;
    }
}
