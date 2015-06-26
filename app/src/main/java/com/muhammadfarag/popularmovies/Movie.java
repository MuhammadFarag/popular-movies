package com.muhammadfarag.popularmovies;

/**
 * Project: Popular Movies
 * Created by muhammad on 27/06/15.
 */
class Movie {
    private String originalTitle;
    private double userRating;
    private String releaseDate;
    private String plotSynopsis;
    private String posterPath;

    public Movie(String originalTitle, double userRating, String releaseDate, String plotSynopsis, String posterPath) {
        this.originalTitle = originalTitle;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
        this.plotSynopsis = plotSynopsis;
        this.posterPath = posterPath;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public double getUserRating() {
        return userRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getPlotSynopsis() {
        return plotSynopsis;
    }

    public String getPosterPath() {
        return posterPath;
    }
}
