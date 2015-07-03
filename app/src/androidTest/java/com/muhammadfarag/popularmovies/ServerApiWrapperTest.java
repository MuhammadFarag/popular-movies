package com.muhammadfarag.popularmovies;

import android.test.AndroidTestCase;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Project: Popular Movies
 * Created by muhammad on 03/07/15.
 */
public class ServerApiWrapperTest extends AndroidTestCase {

    private static final int DEFAULT_SERVER_PAGE_SIZE = 20;
    private int pageSize;

    public void testGivenTotalNumberOfMoviesAndRequiredMoviesPerPageCaclculateNumberOfPages() {
        int totalNumberOfMovies = 1002;
        int requiredPageSize = 40;
        int expected = 26;
        int actual = totalNumberOfMovies / requiredPageSize;
        if (totalNumberOfMovies % requiredPageSize != 0) {
            actual += 1;
        }
        assertEquals("Number of pages calculated is not correct", expected, actual);
    }

    public void testGivenRequiredPageSizeOf40ReturnListOfMoviesOfTheSameSizeForFirstPage() throws Exception {
        int requiredPageSize = 40;
        int page = 1;
        this.pageSize = requiredPageSize;
        List<Movie> movies = getMovies(page);
        int numberOfResultsInCurrentPage = movies.size();

        assertEquals("Number of movies returned in list", requiredPageSize, numberOfResultsInCurrentPage);
    }

    public void testGivenRequiredPageSizeOf60ReturnListOfMoviesOfTheSameSizeForThirdPage() throws Exception {
        int requiredPageSize = 60;
        int page = 3;
        this.pageSize = requiredPageSize;
        List<Movie> movies = getMovies(page);
        int numberOfResultsInCurrentPage = movies.size();

        assertEquals("Number of movies returned in list", requiredPageSize, numberOfResultsInCurrentPage);
    }

    private List<Movie> getMovies(int page) throws IOException, UnauthorizedException, JSONException {
        int numberOfServerPagesPerResult = this.pageSize / DEFAULT_SERVER_PAGE_SIZE;
        int firstRequiredPage = (page - 1) * numberOfServerPagesPerResult + 1;
        int lastRequiredPage = page * numberOfServerPagesPerResult;

        List<Movie> movies = new ArrayList<>();

        MovieDatabaseServerConnector connector = new MovieDatabaseServerConnector(getContext());
        for (int i = firstRequiredPage; i <= lastRequiredPage; i++) {
            String pageData = connector.getPage(i);
            movies.addAll(new DataParser(pageData).getMovies());
        }
        return movies;
    }

}
