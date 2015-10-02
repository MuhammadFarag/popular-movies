package com.muhammadfarag.popularmovies.details;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.muhammadfarag.popularmovies.FavoriteMoviesManager;
import com.muhammadfarag.popularmovies.Movie;
import com.muhammadfarag.popularmovies.R;
import com.squareup.picasso.Picasso;


/**
 * A placeholder fragment containing a simple view.
 */
public class DetailsActivityFragment extends Fragment {

    private FavoriteMoviesManager mManager;

    public DetailsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        mManager = FavoriteMoviesManager.create(getActivity());
        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra("movie")) {
            final Movie movie = intent.getParcelableExtra("movie");

            TextView moviePlot = (TextView) rootView.findViewById(R.id.movie_plot);
            moviePlot.setText("null".equals(movie.getPlotSynopsis()) ? "" : movie.getPlotSynopsis());

            TextView movieTitle = (TextView) rootView.findViewById(R.id.movie_title);
            movieTitle.setText(movie.getOriginalTitle());

            final TextView movieHearted = (TextView) rootView.findViewById(R.id.movie_hearted);
            if (mManager.isFavorite(movie)) {
                movieHearted.setTextColor(Color.BLACK);
            } else {
                movieHearted.setTextColor(Color.LTGRAY);
            }
            movieHearted.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mManager.isFavorite(movie)) {
                        mManager.remove(movie);
                        movieHearted.setTextColor(Color.LTGRAY);
                    } else {
                        mManager.add(movie);
                        movieHearted.setTextColor(Color.BLACK);
                    }
                }
            });

            TextView rating = (TextView) rootView.findViewById(R.id.movie_rating);
            rating.setText(String.valueOf("User Rating [ " + movie.getUserRating() + "/10 ]"));

            TextView releaseDate = (TextView) rootView.findViewById(R.id.movie_release_date);
            releaseDate.setText("Release Date [ " + ("null".equals(movie.getReleaseDate()) ? "N/A" : movie.getReleaseDate()) + " ]");

            ImageView imageView = (ImageView) rootView.findViewById(R.id.movie_poster);
            Picasso.with(getActivity()).load(movie.getPosterUrl()).error(R.drawable.no_poseter_found).into(imageView);

        }
        return rootView;
    }

}