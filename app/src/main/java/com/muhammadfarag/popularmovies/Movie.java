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
    private String posterUrl;

    public Movie(String originalTitle, double userRating, String releaseDate, String plotSynopsis, String posterPath) {
        this.originalTitle = originalTitle;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
        this.plotSynopsis = plotSynopsis;
        this.posterUrl =  "http://image.tmdb.org/t/p/w185" + posterPath;
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

    public String getPosterUrl() {
        return posterUrl;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "originalTitle='" + originalTitle + '\'' +
                ", userRating=" + userRating +
                ", releaseDate='" + releaseDate + '\'' +
                ", plotSynopsis='" + plotSynopsis + '\'' +
                ", posterUrl='" + posterUrl + '\'' +
                '}';
    }
}
